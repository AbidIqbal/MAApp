package com.example.abid.maapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ServicesList extends AppCompatActivity {

    public ListView listView;
    public String[] serviceNames=new String[] {"Hospitals","Hotel","Shopping Mall","Mobile Shops","Tuck Shops","Mechanics","Petrol Pumps","Airports","Sports Shops"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        listView=(ListView) findViewById(R.id.servicesList);
        setListView();

    }

    public void setListView(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ServicesList.this,android.R.layout.simple_list_item_1,serviceNames);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedService=(String) listView.getItemAtPosition(i);
                Intent intent=new Intent(ServicesList.this,Map.class);
                intent.putExtra("serviceName",selectedService);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.services_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addservice:
                startActivity(new Intent(ServicesList.this,RegisterService.class));
                break;
            case R.id.logout:
                Intent i=new Intent(ServicesList.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
