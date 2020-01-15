package com.example.cab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(7000);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    Intent welcomeIntent = new Intent(MainActivity.this,WelcomeActivity.class);
                    startActivity(welcomeIntent);
                }
            }
        };
        thread.start();
    }
    protected void onPause()
    {
        super.onPause();
        finish();
    }
}
