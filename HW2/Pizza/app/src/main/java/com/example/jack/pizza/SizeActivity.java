package com.example.jack.pizza;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SizeActivity extends AppCompatActivity {
    Button small;
    Button medium;
    Button large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);


        // get buttons
        small = (Button) findViewById(R.id.smallButton);
        medium = (Button) findViewById(R.id.mediumButton);
        large = (Button) findViewById(R.id.largeButton);

        // set listeners
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Size", "Small");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Size", "Medium");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("Size", "Large");
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
