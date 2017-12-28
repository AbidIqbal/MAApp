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

/**
 * service list Activity
 * it has a list view to show all the available services.
 */
public class ServicesList extends AppCompatActivity {

    public ListView listView;
    public String[] serviceNames=new String[] {"Hotels","Fast Foods","Shopping Malls","Mobile Shops","Hospitals","Tuck Shops","Car Mechanic","Petrol Pumps","Airports","Sports Shop"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        listView=(ListView) findViewById(R.id.servicesList);
        setListView();

    }

    //this method is used to set listviewdata, adapter, and to handle clicks on any list item
    public void setListView(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ServicesList.this,android.R.layout.simple_list_item_1,serviceNames);
        listView.setAdapter(arrayAdapter);

        //when any list item is clicked, the user will be navigated to map activity
        //the name of the service which is clicked is sent using intent to the next activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedService=(String) listView.getItemAtPosition(i);
                Intent intent=new Intent(ServicesList.this,ServiceMap.class);
                intent.putExtra("serviceName",selectedService);
                startActivity(intent);
            }
        });

    }
    //override methods for creating menu on the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.services_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //override method to handle menu item clicks
    //addservice will open a new activity
    //logout will get the user back on the login activity and all the activity stack will be removed
    // so user cannot come back on the previous activities after logout
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addservice:
                startActivity(new Intent(ServicesList.this,RegisterService.class));
                break;
            case R.id.logout:
                Intent i=new Intent(ServicesList.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
