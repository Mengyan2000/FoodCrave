package com.example.FoodCrave;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private int BACK_ID = 456;

    private ArrayList<String> resultNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get intent from SearchActivity
        Intent search = getIntent();

        // Get restaurant names as extra
        resultNames = search.getStringArrayListExtra("result names");

        // Print restaurant names
        for (int i = 0; i < resultNames.size(); i++) {
            System.out.println(resultNames.get(i));
        }

        // Get reference to the LinearLayout that will hold chunks of UI about the restaurants
        LinearLayout restaurantsList = findViewById(R.id.restaurants_list);
        restaurantsList.removeAllViews();

        // Add an entry to the LinearLayout for each restaurant
        for (int i = 0; i < resultNames.size(); i++) {
            // Create a chunk of UI for this current restaurant
            String name = resultNames.get(i);
            View chunk = getLayoutInflater().inflate(R.layout.chunk_restaurant, restaurantsList, false);

            // Display the restaurant name
            TextView restaurantName = chunk.findViewById(R.id.gameOwner);
            restaurantName.setText(name);

            restaurantsList.addView(chunk);
            System.out.println(name);
        }

//        setContentView(restaurantsList);


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
