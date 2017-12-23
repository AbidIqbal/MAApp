package com.example.abid.maapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ServiceDetail extends AppCompatActivity {

    public Spinner servicename;

    public ArrayList<String> arrayList;
    public ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        servicename=(Spinner) findViewById(R.id.name);
        arrayList=new ArrayList<String>();
        arrayList.add("Hospital");
        arrayList.add("Mobile Shop");

        arrayAdapter=new ArrayAdapter<String>(ServiceDetail.this,android.R.layout.simple_list_item_1,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicename.setAdapter(arrayAdapter);

        servicename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String service=servicename.getSelectedItem().toString();
                Toast.makeText(ServiceDetail.this," "+service,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
