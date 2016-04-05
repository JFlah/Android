package com.example.jack.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent i = getIntent();
        final String title = i.getStringExtra("Title");
        final String desc = i.getStringExtra("Description");
        final String date = i.getStringExtra("Date");

        final int position = i.getIntExtra("Position", 0);

        Button backB = (Button) findViewById(R.id.editBackButton);
        Button editB = (Button) findViewById(R.id.editButton);

        final EditText titleEdit = (EditText) findViewById(R.id.titleEdit);
        final EditText descriptionEdit = (EditText) findViewById(R.id.descriptionEdit);
        TextView dateView = (TextView) findViewById(R.id.dateEditView);

        titleEdit.setText(title);
        descriptionEdit.setText(desc);
        dateView.setText(date);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleEdit.getText().toString().equals(title)) { // title changed
                    String newTitle = titleEdit.getText().toString();
                    updateTitle(newTitle, desc, date, position);
                }
                if (!descriptionEdit.getText().toString().equals(desc)) { // desc changed
                    String newDesc = descriptionEdit.getText().toString();
                    updateDesc(newDesc, title, date, position);
                }
            }
        });

    }

    public void updateTitle(String newTitle, String desc, String date, int position){
        String fileName = "activities.txt";
        Context con = getBaseContext();

        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fis = con.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (i != position) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                } else { // on the line we want
                    String[] theLine = line.split("///");
                    String oldTitle = theLine[0];
                    theLine[0] = newTitle;
                    line = theLine[0] + "///" + theLine[1] + "///" + theLine[2];
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                i++;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        // overwrite whole file with new SB
        try {
            OutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void updateDesc(String newDesc, String title, String date, int position){
        String fileName = "activities.txt";
        Context con = getBaseContext();

        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream fis = con.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (i != position) {
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                } else { // on the line we want
                    String[] theLine = line.split("///");
                    String oldDesc = theLine[1];
                    theLine[1] = newDesc;
                    line = theLine[0] + "///" + theLine[1] + "///" + theLine[2];
                    stringBuilder.append(line);
                    stringBuilder.append("\n");
                }
                i++;
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

        // overwrite whole file with new SB
        try {
            OutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(stringBuilder.toString().getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
