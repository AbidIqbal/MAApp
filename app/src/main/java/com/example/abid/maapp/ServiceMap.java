package com.example.abid.maapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
/**
 * this class is used to show selected service on the map
 * a marker is added on every registered service position
 * it implements two interfaces to load map and to get current user location
 */
public class ServiceMap extends AppCompatActivity implements OnMapReadyCallback, LocationListener {
    GoogleMap map;
    LocationManager locationManager;

    FirebaseDatabase database;
    DatabaseReference myRef;

    TextView NameOfServiceProvider;
    TextView ContactOfServiceProvider;
    TextView ServiceStartTime;
    TextView ServiceEndTime;

    public String [] nameofServiceProvider;
    public String [] contactofservice;
    public String [] startTime;
    public String [] endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        nameofServiceProvider=new String[20];
        contactofservice=new String[20];
        startTime=new String[20];
        endTime=new String[20];


        // getting instance of firebase database to get data
         database= FirebaseDatabase.getInstance();

         //getting selected service name which was sent from serviceList Activity
         Bundle bundle=getIntent().getExtras();
         String service=bundle.getString("serviceName");

                //getting reference of the selected child(Service) from parent user in database
                myRef= database.getReference("user").child(service);

                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        int i=1;
                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            //getting the data object as a model which is used to send data
                            // using RegisterServiceData Model class
                            RegisterServiceData service=snapshot.getValue(RegisterServiceData.class);
                            //this method will shift the user camera view to the received location position
                            gotoLocation(service.getLatitude(),service.getLongitude());

                            //saving received data into arrays to be used for marker
                            nameofServiceProvider[i]=service.getFullName();
                            contactofservice[i]=service.getContact();
                            startTime[i]=service.getStartTime();
                            endTime[i]=service.getEndTime();
                            i++;


//                            if(i==1){
//                                nameofServiceProvider[0]=service.getFullName();
//                                contactofservice[0]=service.getContact();
//                                startTime[0]=service.getStartTime();
//                                endTime[0]=service.getEndTime();
//                                i++;
//                            }
//                            else if(i==2){
//                                nameofServiceProvider[1]=service.getFullName();
//                                contactofservice[1]=service.getContact();
//                                startTime[1]=service.getStartTime();
//                                endTime[1]=service.getEndTime();
//                                i++;
//                            }

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.i("value",error.toString());

                    }

        });


        // Map fragment is used to show map to the user

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        //getting the current location of the user
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, this);
    }

    //override method for menu options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //override method to handle menu options clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            //setting up different map types
            case R.id.mapTypeNone:
                map.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            // case to handle user logout
            // all the activity stack will be destroyed
            case R.id.logout:
                Intent i=new Intent(ServiceMap.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    int count=1;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if(map!=null){
            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v=getLayoutInflater().inflate(R.layout.info_window,null);
                    NameOfServiceProvider=(TextView) v.findViewById(R.id.nameOfUser);
                    ContactOfServiceProvider=(TextView) v.findViewById(R.id.contactOfUser);
                    ServiceStartTime=(TextView) v.findViewById(R.id.startTime);
                    ServiceEndTime=(TextView) v.findViewById(R.id.endTime);
                    if(count==1|| count>2){
                        NameOfServiceProvider.setText(nameofServiceProvider[0]);
                        ContactOfServiceProvider.setText(contactofservice[0]);
                        ServiceStartTime.setText(startTime[0]);
                        ServiceEndTime.setText(endTime[0]);
                        count++;
                    }
                    else if(count==2){
                        NameOfServiceProvider.setText(nameofServiceProvider[1]);
                        ContactOfServiceProvider.setText(contactofservice[1]);
                        ServiceStartTime.setText(startTime[1]);
                        ServiceEndTime.setText(endTime[1]);
                        count++;
                    }



                    LatLng latLng=marker.getPosition();


                    return v;
                }
            });
        }

        // map.setMyLocationEnabled(true);


    }

    public void addmarkerwithdata(double lat, double lng){
        MarkerOptions markerOptions=new MarkerOptions()
                .position(new LatLng(lat,lng));
        map.addMarker(markerOptions);
    }
    public void gotoLocation(double lat, double lng){
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,15);
        map.moveCamera(update);
        addmarkerwithdata(lat,lng);
    }

    @Override
    public void onLocationChanged(Location location) {

//        map.clear();
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("i'm here");

        map.addMarker(markerOptions);

        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 17.0f));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}