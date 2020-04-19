package com.example.greenstreeteats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class SearchActivity extends AppCompatActivity {
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        spinner = (Spinner) findViewById(R.id.spinner);

        // Array of food type options
        String[] options = {"All", "Breakfast", "Bubble tea", "Chinese food", "Coffee", "Fast food", "Greek food",
                "Indian food", "Italian food", "Korean food", "Mexican food", "Sandwiches", "Sushi", "Thai food"};

        // Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),android.R.layout.simple_list_item_1 ,options);

        // Setting Adapter to the Spinner
        spinner.setAdapter(adapter);

        Button search = findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent result = new Intent(SearchActivity.this, ResultActivity.class);
                startActivityForResult(result, 123);
                finish();
                // Code here executes on main thread after user presses button
            }
        });




    }
}
