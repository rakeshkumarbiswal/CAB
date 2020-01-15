package com.example.cab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class customerLoginRegisterActivity extends AppCompatActivity
{
    private Button CustomerLoginButton;
    private Button CustomerRegisterButton;

    private TextView CustomerRegisterLink;
    private TextView CustomerStatus;

    private EditText customeremail;
    private EditText customerpassword;
//____________________________________
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
//____________________________________

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);

        //__________________________________
        mAuth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);
        //_____________________________________

        customeremail=(EditText) findViewById(R.id.customer_email);
        customerpassword=(EditText)findViewById(R.id.customer_password);


        CustomerLoginButton=(Button) findViewById(R.id.customer_login);
        CustomerRegisterButton=(Button) findViewById(R.id.customer_register_btn);
        CustomerRegisterLink=(TextView) findViewById(R.id.register_customer_link);
        CustomerStatus=(TextView) findViewById(R.id.customer_status);

        CustomerRegisterButton.setVisibility(View.INVISIBLE);
        CustomerRegisterButton.setEnabled(false);


        CustomerRegisterLink.setOnClickListener(new View.OnClickListener()
                                                {
                                                    public void onClick(View view)
                                                    {
                                                        CustomerLoginButton.setVisibility(View.INVISIBLE);
                                                        CustomerRegisterLink.setVisibility(View.INVISIBLE);

                                                        CustomerRegisterButton.setVisibility(View.VISIBLE);
                                                        CustomerRegisterButton.setEnabled(true);
                                                        CustomerStatus.setText("Register Customer");
                                                    }
                                                }


        );
        CustomerRegisterButton.setOnClickListener(new View.OnClickListener(){
            public  void onClick(View view)
            {
                String email=customeremail.getText().toString();
                String password=customerpassword.getText().toString();

                Registercustomer(email,password);
            }
        });

        CustomerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=customeremail.getText().toString();
                String password=customerpassword.getText().toString();
                SigninCustomer(email,password);
            }
        });
    }

    private void SigninCustomer(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(customerLoginRegisterActivity.this,"ENTER EMAIL",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(customerLoginRegisterActivity.this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Customer Login");
            loadingBar.setMessage("Please wait while we are checking your credientials ");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(customerLoginRegisterActivity.this,"login successfully",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(customerLoginRegisterActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });

        }

    }

    private void Registercustomer(String email, String password)
    {
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(customerLoginRegisterActivity.this,"ENTER EMAIL",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(customerLoginRegisterActivity.this,"ENTER PASSWORD",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Customer Registration");
            loadingBar.setMessage("Please wait while we are Registering your data ");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(customerLoginRegisterActivity.this,"CustomerRegistered",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(customerLoginRegisterActivity.this, "Customer registration failed", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
            });
        }
    }
}
