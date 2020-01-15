package com.example.cab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriversMapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener
{

    private GoogleMap mMap;

    String userId;
    GoogleApiClient googleApiClient;
    Location lastLocation;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        buildGoogleApiClient();
        int tr=0;
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                Toast.makeText(DriversMapsActivity.this,"ALLOW LOCATION PERMISSION",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            tr=1;
        }
        else
        mMap.setMyLocationEnabled(true);
        if(tr==1)
            Toast.makeText(DriversMapsActivity.this,"GO BACK AND LOGIN AGAIN..",Toast.LENGTH_SHORT).show();

        if(tr==1)
            Toast.makeText(DriversMapsActivity.this,"GO BACK AND LOGIN AGAIN..",Toast.LENGTH_SHORT).show();

        if(tr==1)
            Toast.makeText(DriversMapsActivity.this,"GO BACK AND LOGIN AGAIN..",Toast.LENGTH_SHORT).show();

        if(tr==1)
            Toast.makeText(DriversMapsActivity.this,"GO BACK AND LOGIN AGAIN..",Toast.LENGTH_SHORT).show();

        if(tr==1)
            Toast.makeText(DriversMapsActivity.this,"GO BACK AND LOGIN AGAIN..",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        locationRequest=new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(locationRequest.PRIORITY_HIGH_ACCURACY);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        else
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this);


        ////////////

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        lastLocation=location;

        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        /*--------------------------------------------------------*/
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference Driveravailibilityref=database.getReference().child("Available Drivers");

        GeoFire geoFire =new GeoFire(Driveravailibilityref);
        geoFire.setLocation(userId,new GeoLocation(location.getLatitude(),location.getLongitude()));

        /*--------------------------------------------------------*/

    }

    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient=new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }
    /*-------------------------------------*/

    @Override
    protected void onStop() {
        DatabaseReference Driveravailibilityref;
        Driveravailibilityref = FirebaseDatabase.getInstance().getReference().child("Available Drivers");

        GeoFire geoFire =new GeoFire(Driveravailibilityref);
        geoFire.removeLocation(userId);

        super.onStop();
    }

    /*-------------------------------------*/

}
