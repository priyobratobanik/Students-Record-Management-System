package com.sohini101.studentsrecords;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    Handler handler;
    UserSessionManager session;
    private ImageView img1,img2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        img1=(ImageView)findViewById(R.id.logo_id);
        img2=(ImageView)findViewById(R.id.logo_id1);
        session = new UserSessionManager(getApplicationContext());

        handler=new Handler();// handler is used to hold the screen for specific time and once the handler is out, our main Activity will be launched.
        handler.postDelayed(new Runnable(){//Post Delayed method will delay the time for specified seconds. After the delay time is complete, then your main activity will be launched.

            @Override
            public void run() { //This method will be executed once the timer is over
                if(session.checkLogin()) {
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish();//currently active activity SplashScreen finishes
                }
                else {
                    Intent intent = new Intent(SplashScreen.this,Registration.class);
                    startActivity(intent);
                    finish();
                }
            }
        },2000);
    }
}
