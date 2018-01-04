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

    int sizeOfData=1;

    public MarkerData[] markerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_map);

        markerData=new MarkerData[30];


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

                        for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                            //getting the data object as a model which is used to send data
                            // using RegisterServiceData Model class
                            RegisterServiceData service=snapshot.getValue(RegisterServiceData.class);
                            //this method will shift the user camera view to the received location position
                            try{
                                gotoLocation(service.getLatitude(),service.getLongitude());
                            }
                            catch (Exception e){
                                Toast.makeText(ServiceMap.this,"Network problem",Toast.LENGTH_SHORT);
                            }

                            MarkerData data=new MarkerData(service.getLatitude(),service.getLongitude(),service.getFullName(),service.getContact(),service.getStartTime(),service.getEndTime());
                            markerData[sizeOfData]=data;
                            sizeOfData++;

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
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, this);
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


    /***************************************************************************************
     *    Title: Android Maps: Part 3: Markers
     *    Author: Vivian Aranha
     *    Date Accessed: 26 Dec. 2017
     *    Code version: N/A
     *    Availability: https://www.youtube.com/watch?v=k253ec4m33A
     *
     ***************************************************************************************/


    //override method to add specific things on the map
    //here used to add data to the markers.
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

                    //setting up infowindow with data for marker
                    View v=getLayoutInflater().inflate(R.layout.info_window,null);
                    NameOfServiceProvider=(TextView) v.findViewById(R.id.nameOfUser);
                    ContactOfServiceProvider=(TextView) v.findViewById(R.id.contactOfUser);
                    ServiceStartTime=(TextView) v.findViewById(R.id.startTime);
                    ServiceEndTime=(TextView) v.findViewById(R.id.endTime);

                    double lat=marker.getPosition().latitude;
                    double lng=marker.getPosition().longitude;
                    //getting data from stored array based on location
                    for(int i=1;i<sizeOfData;i++){
                        if(markerData[i].getLatitude()==lat && markerData[i].getLongitude()==lng){
                            NameOfServiceProvider.setText(markerData[i].getFullName());
                            ContactOfServiceProvider.setText(markerData[i].getContact());
                            ServiceStartTime.setText(markerData[i].getStartTime());
                            ServiceEndTime.setText(markerData[i].getEndTime());
                        }
                    }
                    return v;
                }
            });
        }

//         map.setMyLocationEnabled(true);


    }

    //method to add marker on a specific location
    public void addMarkerOnSpecificPoint(double lat, double lng){
        MarkerOptions markerOptions=new MarkerOptions()
                .position(new LatLng(lat,lng));
        map.addMarker(markerOptions);
    }
    //method to load a specific location onto the map
    public void gotoLocation(double lat, double lng){
        LatLng ll=new LatLng(lat,lng);
        CameraUpdate update=CameraUpdateFactory.newLatLngZoom(ll,15);
        map.animateCamera(update);
        addMarkerOnSpecificPoint(lat,lng);
    }

    //override method to add marker on current user location
    @Override
    public void onLocationChanged(Location location) {


        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(currentLocation);
        markerOptions.title("You are here");

        map.addMarker(markerOptions);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10.0f));
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