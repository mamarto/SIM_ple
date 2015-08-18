package com.next.simply.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;

import com.next.simply.R;

public class SplashActivity extends AppCompatActivity {

    private android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<b>SIM PLY</b>"));

        Thread timerThread = new Thread(){
            public void run() {
                try {
                    sleep(1000 * 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, ContactsActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timerThread.start();
    }
}
