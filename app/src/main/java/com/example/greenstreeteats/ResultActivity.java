package com.example.greenstreeteats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class ResultActivity extends AppCompatActivity {

    private String apiKey = "AIzaSyCJ5SXqmg7Gf8eK6SxfAwY_UhpffzwKAsI";

    private FusedLocationProviderClient fusedLocationClient;

    private int PERMISSION_ID = 44;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent search = getIntent();

        // Initialize the SDK
        Places.initialize(getApplicationContext(), apiKey);

        // Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);

        // Create location services client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        if (location != null) {
            System.out.println("!!!!!!!!! LOCATION LOCATION IS NOT NULL!!!!!!!!!");
        } else {
            System.out.println("!!!!!!!!! LOCATION LOCATION IS NULL!!!!!!!!!");
        }

    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            System.out.println("checkPermissions PASSED");
            if (isLocationEnabled()) {
                System.out.println("isLocationEnabled PASSED");
                fusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                System.out.println("~~~~~within function~~~~~");
                                location = task.getResult();
                                if (location == null) {
                                    System.out.println("LOCATION IS NULL WITHIN FUNCTION.");
                                } else {
                                    System.out.println("location is NOT NULL WITHIN FUNCTION");
                                    return;
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

}
