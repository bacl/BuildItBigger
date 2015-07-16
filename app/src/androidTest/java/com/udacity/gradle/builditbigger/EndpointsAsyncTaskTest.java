package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.UiThreadTest;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bruno on 16-07-2015.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase implements EndpointsAsyncTask.TaskCallback{
   private  CountDownLatch countDownLatch;
    private String requestResponse;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        requestResponse=null;
        countDownLatch = new CountDownLatch(1);
    }

    @UiThreadTest
    public void testAsyncTask() throws InterruptedException {

        new EndpointsAsyncTask(this).execute(this.getContext());

        // wait for response
        countDownLatch.await(30, TimeUnit.SECONDS);

        assertFalse(TextUtils.isEmpty(requestResponse));

    }

    @Override
    public void onDone(String result) {
        countDownLatch.countDown();
        requestResponse=result;
    }
}
