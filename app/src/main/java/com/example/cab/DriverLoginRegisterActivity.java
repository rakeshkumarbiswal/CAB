package com.example.cab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DriverLoginRegisterActivity extends AppCompatActivity
{
    private Button DriverLoginButton;
    private Button DriverRegisterButton;
    private TextView DriverRegisterLink;
    private TextView DriverStatus;
    private ProgressDialog loadingBar;

    private FirebaseAuth mAuth;

    private EditText driveremail;
    private EditText driverpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login_register);


        mAuth=FirebaseAuth.getInstance();

        loadingBar=new ProgressDialog(this);
        DriverLoginButton= (Button) findViewById(R.id.driver_login_btn);
        DriverRegisterButton=(Button) findViewById(R.id.driver_register_btn);
        DriverRegisterLink=(TextView) findViewById(R.id.driver_register_link);
        DriverStatus=(TextView) findViewById(R.id.driver_status);


        driveremail=(EditText) findViewById(R.id.email_driver);
        driverpassword=(EditText) findViewById(R.id.password_driver);


        DriverRegisterButton.setVisibility(View.INVISIBLE);
        DriverRegisterButton.setEnabled(false);



        DriverRegisterLink.setOnClickListener(new View.OnClickListener()
                                                {
                                                    public void onClick(View view)
                                                    {
                                                        DriverLoginButton.setVisibility(View.INVISIBLE);
                                                        DriverRegisterLink.setVisibility(View.INVISIBLE);

                                                        DriverRegisterButton.setVisibility(View.VISIBLE);
                                                        DriverRegisterButton.setEnabled(true);
                                                        DriverStatus.setText("   Register Driver");
                                                    }
                                                }


        );
        DriverRegisterButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view)
            {
                String email=driveremail.getText().toString();
                String password=driverpassword.getText().toString();

                Registerdriver(email,password);
            }
        });

        DriverLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=driveremail.getText().toString();
                String password=driverpassword.getText().toString();
                SigninDriver(email,password);
            }
        });

    }

    private void SigninDriver(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"ENTER EMAIL",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Driver Login");
            loadingBar.setMessage("Please wait while we are checking your credientials ");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this,"login successfully",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent driverIntent=new Intent(DriverLoginRegisterActivity.this,DriversMapsActivity.class);
                        startActivity(driverIntent);
                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }

    }


    private void Registerdriver(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"ENTER EMAIL",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLoginRegisterActivity.this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Driver Registration");
            loadingBar.setMessage("Please wait while we are Registering your data ");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this,"driverRegistered",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Intent driverIntent=new Intent(DriverLoginRegisterActivity.this,DriversMapsActivity.class);
                        startActivity(driverIntent);

                    }
                    else
                    {
                        Toast.makeText(DriverLoginRegisterActivity.this, "Driver registration failed", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }
    }
}
