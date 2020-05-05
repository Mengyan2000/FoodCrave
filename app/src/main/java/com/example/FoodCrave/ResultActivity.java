package com.example.FoodCrave;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private int BACK_ID = 456;

    private ArrayList<String> resultNames;

    private ArrayList<Location> resultLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get intent from SearchActivity
        Intent search = getIntent();

        // Get restaurant names as extra
        resultNames = search.getStringArrayListExtra("result names");
        resultLocation = search.getParcelableArrayListExtra("location");

        // Get reference to the LinearLayout that will hold chunks of UI about the restaurants
        LinearLayout restaurantsList = findViewById(R.id.restaurants_list);
        restaurantsList.removeAllViews();

        for (int i = 0; i < resultLocation.size(); i++) {
            System.out.println("location" + resultLocation.get(i).getLatitude());
        }
        // Add an entry to the LinearLayout for each restaurant
        for (int i = 0; i < resultNames.size(); i++) {
            // Create a chunk of UI for this current restaurant
            String name = resultNames.get(i);
            final View chunk = getLayoutInflater().inflate(R.layout.chunk_restaurant, restaurantsList, false);

            // Display the restaurant name
            TextView restaurantName = chunk.findViewById(R.id.restaurantName);
            restaurantName.setText(name);
            Location location = new Location(resultLocation.get(i));
            final double lat = location.getLatitude();
            final double lng = location.getLongitude();

            Button mapButton = chunk.findViewById(R.id.maplink);
            mapButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // When map  button is pressed, open Google Maps app
                    System.out.println("Maps button pressed");

                    Uri directionsUri = Uri.parse("google.navigation:q=" + lat + "," + lng + "&mode=w");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, directionsUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            });

            restaurantsList.addView(chunk);
        }

    }

    // When back button is pressed, go back to SearchActivity
    @Override
    public void onBackPressed() {
        System.out.println("BACK BUTTON PRESSED");
        Intent back = new Intent(ResultActivity.this, SearchActivity.class);
        startActivityForResult(back, BACK_ID);
        finish();
    }

}
