package com.example.abid.maapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Start activity for the application
 * this activity is used for login to the application
 * user has to enter Email and Password to login.
 */
public class LoginActivity extends AppCompatActivity {

    public static Button SignupButton;
    public static Button SigninButton;
    public static EditText Email;
    public static EditText Password;
    private ProgressDialog mprogress;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /***************************************************************************************
         *    Title: How to show progress dialog in Android?
         *    Author: Abhishek Punia
         *    Date Accessed: 21 Dec. 2017
         *    Code version: N/A
         *    Availability: https://stackoverflow.com/questions/10446125/how-to-show-progress-dialog-in-android
         *
         ***************************************************************************************/

        mprogress=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);
        SigninButton = (Button) findViewById(R.id.signin);

        //check whether user is logged in or not
        mAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){

                }
            }
        };

        //on click listener for signin button
        //if this button is clicked onSigninClicked method will be called.
        SigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSigninClicked();
            }
        });
        //method call for signup button
        onSignupClicked();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    //when this method is called, user will be navigated to signup activity
    public void onSignupClicked() {
        SignupButton = (Button) findViewById(R.id.signup);
        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,Signup.class));

            }
        });
    }

    //method for signing in user to the applicatin
    public void onSigninClicked() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        //check whether any field is empty or not
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Email or password field is  empty",Toast.LENGTH_LONG).show();
        }
        else{
            //set progress dialog for the user to know that some process is running
            mprogress.setMessage("Signing in");
            mprogress.show();
            //Authentication of user using firebase authentication service.
            //method call to signin using email and password.

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //if the task is not completed successfully, it means user is not signed in.
                            if ((!task.isSuccessful())) {

                                Toast.makeText(LoginActivity.this,"Sign in problem",Toast.LENGTH_LONG).show();
                                mprogress.dismiss();

                            }
                            else{

                                //task completed successfully
                                //tell the user and navigate to next activity which is of list of services.
                                // finish this activity so that it is removed from the activity stack to restrict user from coming back to this activity using back button.
                                Toast.makeText(LoginActivity.this,"Sign in successful",Toast.LENGTH_LONG).show();
                                mprogress.dismiss();
                                startActivity(new Intent(LoginActivity.this,ServicesList.class));
                                finish();

                            }
                        }
                    });
        }

    }
}
