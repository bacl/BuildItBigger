package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;

import com.baclpt.joker.backend.myApi.MyApi;
import com.baclpt.jokesdisplayer.JokeTellerActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by Bruno on 16-07-2015.
 */
public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static final String LOG_TAG = EndpointsAsyncTask.class.getCanonicalName();
    public static final String WEB_SERVICE_URL = "https://bigbuilderjoker.appspot.com/_ah/api/";

    private static MyApi myApiService = null;
    private Context context;

    // callback to alert the observer
    private TaskCallback mCallback;

    public EndpointsAsyncTask() {
        super();
        mCallback = null;
    }

    public EndpointsAsyncTask(TaskCallback callback) {
        super();
        mCallback = callback;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        String appengUrl;
        if (params != null && params.length > 0) {
            context = params[0].first;
            appengUrl = params[0].second;
        } else {
            appengUrl = WEB_SERVICE_URL;
        }

        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl(appengUrl);
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getLocalizedMessage(), e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        //alert observers
        if (mCallback != null) mCallback.onDone(result);

        if (result != null && context != null) {
            Intent displayJokeIntent = new Intent(context, JokeTellerActivity.class);
            displayJokeIntent.putExtra(JokeTellerActivity.JOKE_TEXT_EXTRA, result);

            context.startActivity(displayJokeIntent);
        }
    }

    /**
     * Callback interface
     */
    public interface TaskCallback {
        void onDone(String result);
    }
}