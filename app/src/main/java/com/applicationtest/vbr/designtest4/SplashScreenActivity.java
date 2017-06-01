package com.applicationtest.vbr.designtest4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.applicationtest.vbr.designtest4.com.vbr.login.LoginActivity1;

/**
 * Created by C5245675 on 4/8/2017.
 */
public class SplashScreenActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this,LoginActivity1.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}
