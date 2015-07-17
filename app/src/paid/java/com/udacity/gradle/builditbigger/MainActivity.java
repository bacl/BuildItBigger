package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.TaskCallback {
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

    }

    public void tellJoke(View view) {
        // show spinner
        spinner.setVisibility(View.VISIBLE);

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
