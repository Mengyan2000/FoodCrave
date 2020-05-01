package com.example.greenstreeteats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
                "Ice cream", "Indian food", "Italian food", "Korean food", "Mexican food", "Sandwiches", "Sushi", "Thai food"};

        // Declaring an Adapter and initializing it to the data pump
        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),android.R.layout.simple_list_item_1 ,options);

        // Setting Adapter to the Spinner
        spinner.setAdapter(adapter);
        //final String text = spinner.getSelectedItem().toString();

        final String[] item = new String[1];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                item[0] = (String) parent.getItemAtPosition(pos);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        System.out.println(item[0]);
        Button search = findViewById(R.id.Search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent result = new Intent(SearchActivity.this, ResultActivity.class);
                result.putExtra("option", item[0]);
                startActivityForResult(result, 123);
                finish();
                // Code here executes on main thread after user presses button
            }
        });




    }
}
