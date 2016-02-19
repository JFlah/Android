package com.example.jack.pizza;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class VegActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> vegList;
    Button doneButton;
    String currentVeg;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg);

        // set home image
        ImageView image = (ImageView) findViewById(R.id.vegImage);
        image.setImageResource(R.mipmap.ic_launcher);

        vegList = new ArrayList<>();
        doneButton = (Button) findViewById(R.id.doneButtonVeg);
        listView = (ListView) findViewById(R.id.vegListView);

        final String[] veggies = new String[] { "mushrooms",
                                            "peppers",
                                            "onions",
                                            "spinach",
                                            "tomatoes",
                                            "broccoli" };
        // adapter stuff
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, veggies);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // listview item onclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                currentVeg = (String) listView.getItemAtPosition(position);
                listView.setItemChecked(position, true);
                if (!vegList.contains(currentVeg))
                    vegList.add(currentVeg);
            }
        });

        // done button onclick
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send vegList
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("Veggies", vegList);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
