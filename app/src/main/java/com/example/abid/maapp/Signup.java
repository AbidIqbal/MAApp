package com.example.abid.maapp;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    public static Button RegisterButton;
    public static EditText Email;
    public static EditText Password;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        RegisterButton=(Button) findViewById(R.id.register);
        Email=(EditText) findViewById(R.id.email);
        Password=(EditText) findViewById(R.id.password);

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=Email.getText().toString();
                String password=Password.getText().toString();

                if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
                    Toast.makeText(Signup.this,"Some fields are empty",Toast.LENGTH_LONG).show();

                }
                else{

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Signup.this, "You are Registered, Now signin", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Signup.this,MainActivity.class));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                }
                            });
                }


            }
        });

    }
}