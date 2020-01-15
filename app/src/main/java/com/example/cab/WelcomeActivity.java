package com.example.cab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private Button WelcomeDriverButton;
    private Button WelcomeCustomerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome2);

        WelcomeDriverButton=(Button) findViewById(R.id.button8);
        WelcomeCustomerButton=(Button) findViewById(R.id.button9);

        WelcomeCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent LoginRegisterCustomerIntent =new Intent(WelcomeActivity.this,customerLoginRegisterActivity.class);
                startActivity(LoginRegisterCustomerIntent);
            }
        });


        WelcomeDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent LoginRegisterDriverIntent = new Intent(WelcomeActivity.this, DriverLoginRegisterActivity.class);
                startActivity(LoginRegisterDriverIntent);
            }
        });
    }
}
