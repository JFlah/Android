package com.example.jack.sqlparta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView text = (TextView) findViewById(R.id.showView);
        Intent intent = getIntent();

        String title = intent.getStringExtra("Title");
        String description = intent.getStringExtra("Description");
        String date = intent.getStringExtra("Date");

        text.setText("Title: " + title + "\nDescription: " + description + "\nDate: " + date);
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
