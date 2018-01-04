package com.example.abid.maapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A class for showing Map to the user
 * Used for Service Location Selection.
 * It has dragable marker to select location.
 * uses Current user location to place initial marker.
 */
public class AddServiceLocationMap extends AppCompatActivity implements OnMapReadyCallback,LocationListener {

    LocationManager locationManager;
    GoogleMap myMap;

    public Double latitude;
    public Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_location_map);
        //Adding alert dialog to tell user how to select the location

        /***************************************************************************************
         *    Title: How to change textcolor in AlertDialog
         *    Author: Mohammad
         *    Date Accessed: 20 Dec. 2017
         *    Code version: N/A
         *    Availability: https://stackoverflow.com/questions/33437398/how-to-change-textcolor-in-alertdialog
         *
         ***************************************************************************************/

        AlertDialog.Builder myBuilder=new AlertDialog.Builder(AddServiceLocationMap.this);
        myBuilder.setMessage(Html.fromHtml("<font color='#000000'>Drag Marker to select the Location</font>"))
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       dialogInterface.cancel();
                    }
                });
        AlertDialog alert=myBuilder.create();
        alert.setTitle(Html.fromHtml("<font color='#000000'>Information</font>"));
        alert.show();


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mymapfragment);
        mapFragment.getMapAsync(this);

    }



    /***************************************************************************************
     *    Title: Android Maps: Part 1: Basic Maps
     *    Author: Vivian Aranha
     *    Date Accessed: 10 Dec. 2017
     *    Code version: N/A
     *    Availability: https://www.youtube.com/watch?v=lchyOhPREh4
     *
     ***************************************************************************************/

    //method for setting up map
    //it is coming from OnMapReadyCallback interface



    public void onMapReady(final GoogleMap map) {
        myMap = map;

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


        /***************************************************************************************
         *    Title: LocationManager
         *    Author: Android Developers
         *    Date Accessed: 20 Dec. 2017
         *    Code version: N/A
         *    Availability: https://developer.android.com/reference/android/location/LocationManager.html#requestLocationUpdates(long, float, android.location.Criteria, android.app.PendingIntent)
         *
         ***************************************************************************************/

        //setting up  current location on the map
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1,  this);



        /***************************************************************************************
         *    Title: Android Maps: Part 3: Markers
         *    Author: Vivian Aranha
         *    Date Accessed: 20 Dec. 2017
         *    Code version: N/A
         *    Availability: https://www.youtube.com/watch?v=k253ec4m33A
         *
         ***************************************************************************************/


        //listener for marker drag
        myMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                //here onMarkerDragEnd the location of the service is taken
                //confirmed by the user using AlertDialog.
                //if user says Yes, the selected coordinates of the location are sent to RegisterService Activity using intent as Extra.

                final LatLng ll=marker.getPosition();
                marker.setTitle(Double.toString(ll.latitude));
                AlertDialog.Builder myBuilder=new AlertDialog.Builder(AddServiceLocationMap.this);
                myBuilder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure you want to select this location??</font>"))
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                latitude=ll.latitude;
                                longitude=ll.longitude;
                                Intent intent=new Intent(AddServiceLocationMap.this,RegisterService.class);
                                intent.putExtra("latitude",latitude);
                                intent.putExtra("longitude",longitude);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert=myBuilder.create();
                alert.setTitle(Html.fromHtml("<font color='#000000'>Confirmation!!</font>"));
                alert.show();

            }
        });
    }

    //Override method to add marker with options and move camera to the location of marker (Location of the user)
    @Override
    public void onLocationChanged(Location location) {


        myMap.clear();
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.draggable(true);
        markerOptions.position(currentLocation);
        markerOptions.title("You are here");

        myMap.addMarker(markerOptions);
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10.0f));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /***************************************************************************************
     *    Title: Android SDK: Implement an Options Menu
     *    Author: Sue Smith
     *    Date Accessed: 20 Dec. 2017
     *    Code version: N/A
     *    Availability: https://code.tutsplus.com/tutorials/android-sdk-implement-an-options-menu--mobile-9453
     *
     ***************************************************************************************/


    //override method to add menu options into the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //override method to handle different menu item clicks.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.mapTypeNormal:
                myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.logout:
                Intent i=new Intent(AddServiceLocationMap.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}

