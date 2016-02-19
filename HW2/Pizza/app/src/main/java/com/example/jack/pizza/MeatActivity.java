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

public class MeatActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> meatList;
    Button doneButton;
    String currentMeat;
    int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meat);

        // set home image
        ImageView image = (ImageView) findViewById(R.id.meatImage);
        image.setImageResource(R.mipmap.meat);

        meatList = new ArrayList<>();
        doneButton = (Button) findViewById(R.id.doneButtonMeat);
        listView = (ListView) findViewById(R.id.meatListView);

        final String[] meats = new String[] { "pepperoni",
                "sausage",
                "ham",
                "turkey",
                "chicken",
                "goat",
                "horse",
                "dog",
                "cat",
                "human"};
        // adapter stuff
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, meats);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // listview item onclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                currentMeat = (String) listView.getItemAtPosition(position);
                listView.setItemChecked(position, true);
                if (!meatList.contains(currentMeat))
                    meatList.add(currentMeat);
            }
        });

        // done button onclick
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send meatList
                Intent returnIntent = new Intent();
                returnIntent.putStringArrayListExtra("Meats", meatList);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
