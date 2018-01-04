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

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * This class is for registering a new service by getting location from AddServiceLocationMap and data from the view
 * New service is registered at Firebase RealTime Database.
 */
public class RegisterService extends AppCompatActivity {

    public Button selectLocation;
    public Spinner serviceName;
    public EditText fullName;
    public EditText contact;
    public EditText startingTime;
    public EditText endingTime;
    public Button registerService;
    private ProgressDialog mprogress;


    public double latitude=0.0;
    public double longitude=0.0;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);

        mprogress=new ProgressDialog(this);
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


        //onClick listener for select location button
        //user is navigated to the Map Activity to select location
        selectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterService.this,AddServiceLocationMap.class));
            }
        });


        /***************************************************************************************
         *    Title: TimePicker Dialog from clicking EditText
         *    Author: Robadob
         *    Date Accessed: 23 Dec. 2017
         *    Code version: N/A
         *    Availability: https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
         *
         ***************************************************************************************/

        //onClick listener for starting time selection
        //Time picker dialog appears on clicking it
        //user can select time in 24 hour format.

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

        //onClick listener for ending time selection
        //Time picker dialog appears on clicking it
        //user can select time in 24 hour format.
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

        //getting referance of firebase database
        database = FirebaseDatabase.getInstance();
        //onClick listener for register button.
        //if all the information provided is not null the service will be registered to firebase database.
        registerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    //getting location attributes sent by Map activity in intent Extra's.
                    Bundle bundle=getIntent().getExtras();
                    latitude=bundle.getDouble("latitude");
                    longitude=bundle.getDouble("longitude");
                }
                catch (Exception e){
                    Toast.makeText(RegisterService.this,"No location selected",Toast.LENGTH_SHORT).show();
                }
                String FullName=fullName.getText().toString();
                String ContactNum=contact.getText().toString();
                String service=serviceName.getSelectedItem().toString();
                String startTime=startingTime.getText().toString();
                String endTime=endingTime.getText().toString();

                /***************************************************************************************
                 *    Title: Check if EditText is empty
                 *    Author: MilapTank
                 *    Date Accessed: 23 Dec. 2017
                 *    Code version: N/A
                 *    Availability: https://stackoverflow.com/questions/6290531/check-if-edittext-is-empty
                 *
                 ***************************************************************************************/

                if( TextUtils.isEmpty(FullName)|| TextUtils.isEmpty(ContactNum)|| TextUtils.isEmpty(service)|| TextUtils.isEmpty(startTime)|| TextUtils.isEmpty(endTime)){
                    Toast.makeText(RegisterService.this,"Some fields are empty",Toast.LENGTH_SHORT).show();

                }
                else{

                    //if data is not null then adddata method is called
                    //this method will add service to the firebase database.
                    mprogress.setMessage("Registering");
                    mprogress.show();
                    adddata(latitude,longitude,service,FullName,ContactNum,startTime,endTime);
                }


            }
        });


    }
    //Method to add service
    //after getting referance to the parent called user, new service will be added as a child in category of it's serviceName
    //If the child with same serviceName is already created, new child will be added in that category with random id.

    public void adddata(double lat,double lng,String servicename,String fullname, String contactnum,String startTime,String endTime){
        myRef =database.getReference("user").child(servicename);
        RegisterServiceData serviceData=new RegisterServiceData(lat,lng,servicename,fullname,contactnum,startTime,endTime);
        Task<Void> result= myRef.push().setValue(serviceData);
        if(!result.isSuccessful()){
            Toast.makeText(RegisterService.this, "Service Added", Toast.LENGTH_SHORT).show();
            mprogress.dismiss();
        }
        else {
            Toast.makeText(RegisterService.this, "Failed", Toast.LENGTH_SHORT).show();
            mprogress.dismiss();
        }
    }
}
