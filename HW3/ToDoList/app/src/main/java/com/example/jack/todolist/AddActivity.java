package com.example.jack.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Jack on 3/9/2016.
 */
public class AddActivity extends AppCompatActivity {
    Button add;
    EditText title;
    EditText description;
    String titleString;
    String descriptionString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // get Button(s)
        add = (Button) findViewById(R.id.add_button);

        // get text
        title = (EditText) findViewById(R.id.add_title_edit);
        description = (EditText) findViewById(R.id.add_description_edit);

        // set listener(s)
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView error = (TextView) findViewById(R.id.error);

                // get texts
                titleString = title.getText().toString();
                descriptionString = description.getText().toString();
                if (!titleString.isEmpty() && !descriptionString.isEmpty()) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("Title", titleString);
                    returnIntent.putExtra("Description", descriptionString);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    if (titleString.isEmpty() && descriptionString.isEmpty())
                        error.setText("Please enter a title and description.");
                    else if (titleString.isEmpty() && !descriptionString.isEmpty())
                        error.setText("Please enter a title.");
                    else if (!titleString.isEmpty() && descriptionString.isEmpty())
                        error.setText("Please enter a description.");
                }
            }
        });

    }
}
