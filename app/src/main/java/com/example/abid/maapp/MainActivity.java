package com.example.abid.maapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public static Button SignupButton;
    public static Button SigninButton;
    public static EditText Email;
    public static EditText Password;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);

        SigninButton = (Button) findViewById(R.id.signin);

        //check whether user is logged in or not
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Toast.makeText(MainActivity.this,"Successful intent",Toast.LENGTH_LONG).show();
                }
            }
        };

        SigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSigninClicked();
            }
        });
        onSignupClicked();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onSignupClicked() {
        SignupButton = (Button) findViewById(R.id.signup);

        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Signup.class));
//                Intent intent = new Intent("com.example.abid.maapp.Signup");
//                startActivity(intent);
            }
        });
    }

    public void onSigninClicked() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        Log.i("data",email+password);

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this,"Email or password is empty",Toast.LENGTH_LONG).show();

        }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if ((!task.isSuccessful())) {

                                Toast.makeText(MainActivity.this,"Sign in problem",Toast.LENGTH_LONG).show();

                            }
                            else{

                                Toast.makeText(MainActivity.this,"Sign in ho gea",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MainActivity.this,Map.class));

                            }
                        }
                    });
        }

    }
}
