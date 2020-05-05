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

        Intent search = getIntent();

        resultNames = search.getStringArrayListExtra("result names");
        System.out.println("IN RESULT ACTIVITY");
        System.out.println("EXTRA SIZE: " + resultNames.size());
        for (int i = 0; i < resultNames.size(); i++) {
            System.out.println(resultNames.get(i));
        }
        setContentView(R.layout.activity_result);
        //LinearLayout result = findViewById(R.layout.activity_result);
        LinearLayout restaurant = findViewById(R.id.restaurants_list);

        for (int i = 0; i < resultNames.size(); i++) {

            String name = resultNames.get(i);
            View chunk = getLayoutInflater().inflate(R.layout.chunk_restaurant, restaurant, false);

            TextView single = chunk.findViewById(R.id.gameOwner);

            single.setText(name);
            restaurant.addView(chunk);
            System.out.println(name + "gameOwner");
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
