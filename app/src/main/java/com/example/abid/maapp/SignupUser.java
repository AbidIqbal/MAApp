package com.example.abid.maapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupUser extends AppCompatActivity {

    public static Button RegisterButton;
    public static Button SelectLocation;
    public static EditText FirstName;
    public static EditText SecondName;
    public static EditText UserName;
    public static EditText Email;
    public static EditText Contact;
    public static EditText Address;
    public static EditText Password;
    public static Spinner spinner;
    public String service;
    public static TextView LocationStatus;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    MyMap myMap=new MyMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_user);


        RegisterButton=(Button) findViewById(R.id.register);

        SelectLocation=(Button) findViewById(R.id.LocationButton);



        spinner=(Spinner) findViewById(R.id.servicesspinner);
        ArrayAdapter<CharSequence> myAdapter= ArrayAdapter.createFromResource(SignupUser.this,R.array.services,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);


        FirstName=(EditText) findViewById(R.id.firstname);
        SecondName=(EditText) findViewById(R.id.secondname);
        UserName=(EditText) findViewById(R.id.username);
        Email=(EditText) findViewById(R.id.email);
        Contact=(EditText) findViewById(R.id.contact);
        Address=(EditText) findViewById(R.id.address);
        Password=(EditText) findViewById(R.id.password);
        LocationStatus=(TextView) findViewById(R.id.locationstatusinfo);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User").push();




        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstName=FirstName.getText().toString();
                String secondName=SecondName.getText().toString();
                String userName=UserName.getText().toString();
                String email=Email.getText().toString();
                String contact=Contact.getText().toString();
                String address=Address.getText().toString();
                String password=Password.getText().toString();
                service=spinner.getSelectedItem().toString();


                if(TextUtils.isEmpty(firstName) || TextUtils.isEmpty(secondName)|| TextUtils.isEmpty(userName)|| TextUtils.isEmpty(email)||
                        TextUtils.isEmpty(contact)|| TextUtils.isEmpty(address)|| TextUtils.isEmpty(password)){
                    Toast.makeText(SignupUser.this,"Some fields are empty",Toast.LENGTH_LONG).show();

                }
                else{


                    Double latitude=myMap.latitude;
                    Double longitude=myMap.longitude;
                    LocationStatus.setText("Location selected");


                    DatabaseReference child1 = myRef.child("First Name");
                    child1.setValue(firstName);
                    DatabaseReference child2 = myRef.child("Second Name");
                    child2.setValue(secondName);
                    DatabaseReference child3 = myRef.child("Latitude");
                    child3.setValue(latitude);
                    DatabaseReference child4 = myRef.child("Longitude");
                    child4.setValue(longitude);
                    DatabaseReference child5 = myRef.child("Contact");
                    child5.setValue(contact);
                    DatabaseReference child6 = myRef.child("Address");
                    child6.setValue(address);
                    DatabaseReference child7 = myRef.child("Password");
                    child7.setValue(password);
                    DatabaseReference child8 = myRef.child("Service");
                    child8.setValue(service);

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupUser.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(SignupUser.this, "You are Registered, Now signin", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupUser.this,MainActivity.class));

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignupUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                }
                            });
                }


            }
        });
        SelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupUser.this,MyMap.class));
            }
        });

    }

}
