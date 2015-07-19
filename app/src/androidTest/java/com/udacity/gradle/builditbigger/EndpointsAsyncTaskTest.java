package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bruno on 16-07-2015.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase implements EndpointsAsyncTask.TaskCallback {
    private CountDownLatch countDownLatch;
    private String requestResponse;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        requestResponse = null;
        countDownLatch = new CountDownLatch(1);
    }

    @UiThreadTest
    public void testAsyncTask() throws InterruptedException {

        final String localAppEngUrl = "http://192.168.56.1:8080/_ah/api/"; // 10.0.2.2
        new EndpointsAsyncTask(this).execute(new Pair<Context, String>(null, localAppEngUrl));

        // wait for response
        countDownLatch.await();

        assertFalse(TextUtils.isEmpty(requestResponse));

    }

    @Override
    public void onDone(String result) {
        countDownLatch.countDown();
        requestResponse = result;
    }
}
