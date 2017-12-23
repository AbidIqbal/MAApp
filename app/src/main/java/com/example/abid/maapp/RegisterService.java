package com.example.abid.maapp;

import android.app.*;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.provider.CalendarContract.Reminders;
import android.provider.CalendarContract.Calendars;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterService extends AppCompatActivity {

    public Button selectLocation;
    public Spinner serviceName;
    public EditText fullName;
    public EditText contact;
    public EditText startingTime;
    public EditText endingTime;
    public Button registerService;

    public double latitude=0.0;
    public double longitude=0.0;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");
//        Toast.makeText(RegisterService.this,"Donene",Toast.LENGTH_SHORT).show();



        selectLocation=(Button) findViewById(R.id.selectLocationButton);

        serviceName=(Spinner) findViewById(R.id.typeOfService);
        ArrayAdapter<CharSequence> myAdapter= ArrayAdapter.createFromResource(RegisterService.this,R.array.services,android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceName.setAdapter(myAdapter);

        fullName=(EditText) findViewById(R.id.nameOfServiceProvider);
        contact=(EditText) findViewById(R.id.contactOfService);
        startingTime=(EditText) findViewById(R.id.fromTime);
        endingTime=(EditText) findViewById(R.id.toTime);
        registerService=(Button) findViewById(R.id.registerServiceButton);


        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterService.this,MyMap.class));
            }
        });

        startingTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime=Calendar.getInstance();
                int hour=mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute=mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker=new TimePickerDialog(RegisterService.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int SelectedHour, int SelectedMinute) {
                        startingTime.setText(""+SelectedHour+":"+String.format("%02d",SelectedMinute));
                    }
                },hour,minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        endingTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar mCurrentTime=Calendar.getInstance();
                int hour=mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute=mCurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker=new TimePickerDialog(RegisterService.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int SelectedHour, int SelectedMinute) {
                        endingTime.setText(""+SelectedHour+":"+String.format("%02d",SelectedMinute));
                    }
                },hour,minute,true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        database = FirebaseDatabase.getInstance();

        registerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=getIntent().getExtras();
                latitude=bundle.getDouble("latitude");
                longitude=bundle.getDouble("longitude");
                Toast.makeText(RegisterService.this,"data received",Toast.LENGTH_SHORT).show();
                String FullName=fullName.getText().toString();
                String ContactNum=contact.getText().toString();
                String service=serviceName.getSelectedItem().toString();
                String startTime=startingTime.getText().toString();
                String endTime=endingTime.getText().toString();

                if(latitude==0.0 || longitude==0.0|| TextUtils.isEmpty(FullName)|| TextUtils.isEmpty(ContactNum)|| TextUtils.isEmpty(service)|| TextUtils.isEmpty(startTime)|| TextUtils.isEmpty(endTime)){
                    Toast.makeText(RegisterService.this,"Some fields are empty",Toast.LENGTH_SHORT).show();

                }
                else{


                    adddata(latitude,longitude,service,FullName,ContactNum,startTime,endTime);
                }


            }
        });


    }
    public void adddata(double lat,double lng,String servicename,String fullname, String contactnum,String startTime,String endTime){
        myRef =database.getReference("user").child(servicename);
        RegisterServiceData serviceData=new RegisterServiceData(lat,lng,servicename,fullname,contactnum,startTime,endTime);
        myRef.push().setValue(serviceData);
        Toast.makeText(RegisterService.this, "Registered", Toast.LENGTH_SHORT).show();
    }
}
