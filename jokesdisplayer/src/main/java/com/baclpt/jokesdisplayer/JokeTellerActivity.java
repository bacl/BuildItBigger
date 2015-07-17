package com.baclpt.jokesdisplayer;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TextView;


public class JokeTellerActivity extends ActionBarActivity {

    public static final String JOKE_TEXT_EXTRA = "joke_text_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_teller);
        setTitle(R.string.title_random_joke);

        final Intent intent = getIntent();
        final String joke;

        if (intent != null && intent.getStringExtra(JOKE_TEXT_EXTRA) != null) {
            joke = intent.getStringExtra(JOKE_TEXT_EXTRA);
        } else {
            joke = getString(R.string.error_msg_joke_not_found);
        }

        final TextView tvJoke = (TextView) findViewById(R.id.joke_text_view);
        tvJoke.setText(joke);
    }
}
