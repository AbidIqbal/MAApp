package com.example.abid.maapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A class to manage user signup
 * creates user onto the firebase realtime authentication database service.
 */
public class Signup extends AppCompatActivity {

    public static Button SignupButton;
    public static EditText Email;
    public static EditText Password;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mprogress=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        SignupButton=(Button) findViewById(R.id.register);
        Email=(EditText) findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);

        //onClick listener for Signup button
        //if the textfields are not empty, user will be created.
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=Email.getText().toString();
                String password=Password.getText().toString();


                /***************************************************************************************
                 *    Title: Check if EditText is empty
                 *    Author: MilapTank
                 *    Date Accessed: 23 Dec. 2017
                 *    Code version: N/A
                 *    Availability: https://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
                 *
                 ***************************************************************************************/
                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this,"Some fields are empty",Toast.LENGTH_LONG).show();

                }
                else{

                    mprogress.setMessage("Signing up");
                    mprogress.show();
                    //Firebase authentication method call to create user with email and password.
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        //if signup is succeeded inform the user
                                        mprogress.dismiss();
                                        Toast.makeText(Signup.this, "You are Registered, Now signin", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Signup.this,LoginActivity.class));

                                    } else {
                                        // If signup fails, display a message to the user.
                                        mprogress.dismiss();
                                        Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }


            }
        });

    }
}