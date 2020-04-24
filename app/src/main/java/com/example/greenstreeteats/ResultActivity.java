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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private String apiKey = "AIzaSyCJ5SXqmg7Gf8eK6SxfAwY_UhpffzwKAsI";

    private FusedLocationProviderClient fusedLocationClient;

    private int PERMISSION_ID = 44;

    private Location location;

    private String option;

    private int radius = 800;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent search = getIntent();

        option = search.getStringExtra("option");
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
                                    System.out.println(location);
                                    getResult();
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

    private void getResult() {

        final JsonObject[] places = new JsonObject[1];

        System.out.println(option);
        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://maps.googleapis.com/maps/api/place/textsearch/json?key=" + apiKey
                + "&query=" + option
                + "&location=" + location.getLatitude() + ", " + location.getLongitude()+ "&radius=" + radius
                + "&opennow";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        JsonParser parse = new JsonParser();
                        places[0] = (JsonObject) parse.parse(response);
                        System.out.println(places[0]);
                        JsonArray results = (JsonArray) places[0].get("results");
                        for (JsonElement result : results) {
                            String name = ((JsonObject) result).get("name").getAsString();
                            System.out.println(name);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
