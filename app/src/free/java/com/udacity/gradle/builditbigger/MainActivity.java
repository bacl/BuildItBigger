package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.TaskCallback {
    private InterstitialAd mInterstitialAd;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);


        // Adding interstitial ads to an activity
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        requestNewInterstitial();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                tellJokeNow();
            }
        });
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("1B17F4FB49E2DB61CE2E600CCB17E689")
                .build();
        mInterstitialAd.loadAd(adRequest);

    }


    public void tellJoke(View view) {
        // show spinner
        spinner.setVisibility(View.VISIBLE);

        // load interstitial ad if available
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            tellJokeNow();
        }
    }

    public void tellJokeNow() {
        // start async task to get joke from app engine server
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, EndpointsAsyncTask.WEB_SERVICE_URL));
    }

    @Override
    public void onDone(String result) {
        // hide spinner
        spinner.setVisibility(View.INVISIBLE);

        // check if it was successful on retrieving a joke, if not show error msg
        if (result == null)
            Toast.makeText(this, R.string.error_msg_connection, Toast.LENGTH_SHORT).show();
    }
}
