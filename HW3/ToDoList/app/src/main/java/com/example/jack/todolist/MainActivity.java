package com.example.jack.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ListView list;
    ListAdapter adapter;
    public MainActivity mainActivity = null;
    public ArrayList<ToDo> valuesArr = new ArrayList<>();

    static ArrayList<Integer> toDeleteArray = new ArrayList<>();

    Button add;
    Button clear;
    Button deleteSelected;
    StringBuilder sb;
    Context con;
    String fileName = "activities.txt";
    File file = new File(fileName);
    FileOutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = this;

        con = getBaseContext();

        // make list Text file
        if (!file.exists())
            makeFile();

        setListData();

        Resources res = getResources();
        list = (ListView) findViewById(R.id.toToListView);
        list.setChoiceMode(list.CHOICE_MODE_MULTIPLE);

        adapter = new ListAdapter(mainActivity, valuesArr, res);
        list.setAdapter(adapter);

        // get button(s)
        add = (Button) findViewById(R.id.add_button);
        clear = (Button) findViewById(R.id.clear_button);
        deleteSelected = (Button) findViewById(R.id.deleteSelectedButton);

        // set listener(s)
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddActivity.class);
                startActivityForResult(intent, 001);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file.exists())
                    con.deleteFile(fileName);
                valuesArr = new ArrayList<>();
                toDeleteArray = new ArrayList<>();

                Resources res = getResources();
                list = (ListView) findViewById(R.id.toToListView);

                adapter = new ListAdapter(mainActivity, valuesArr, res);
                list.setAdapter(adapter);
            }
        });

        deleteSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        deleteSelectedLines(toDeleteArray);
                        toDeleteArray = new ArrayList<>();
                        dialog.dismiss();
                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    // activity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String description;
        String title;

        switch (requestCode) {
            // adding item
            case 001:
                if (resultCode == Activity.RESULT_OK) {
                    title = data.getStringExtra("Title");
                    description = data.getStringExtra("Description");

                    updateFile(title, description);
                    setListData();
                    Resources res = getResources();
                    list = (ListView) findViewById(R.id.toToListView);
                    adapter = new ListAdapter(mainActivity, valuesArr, res);
                    list.setAdapter(adapter);
                }
        }
    }

    // set data to arraylist
    public void setListData() {
        try {
            FileInputStream fis = con.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {

                String[] elements = line.split("///");
                String title = elements[0];
                String description = elements[1];
                String date = elements[2];
                ToDo toDo = new ToDo(title, description, date);
                int i = 0;
                for (ToDo theTo : valuesArr) {
                    if (theTo.getTitle().equals(toDo.getTitle()))
                        break;
                    i++;
                }
                if (i == valuesArr.size()) {
                    valuesArr.add(toDo);
                    Toast.makeText(getBaseContext(), "New Item Added!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void deleteSelectedLines(ArrayList<Integer> linesToDelete) {
        String fileName = "activities.txt";
        Context con = getBaseContext();


        // READING FROM FILE, DELETING CORRECT ONES
        int z = 0;
        Collections.sort(linesToDelete);
        for (int position : linesToDelete) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean oneDone = false;
            if (valuesArr.size() > 0)
                valuesArr.remove(position-z);

            try {
                FileInputStream fis = con.openFileInput(fileName);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);
                String line;
                int i = 0;

                while ((line = bufferedReader.readLine()) != null) {
                    if (i != position-z) {
                        stringBuilder.append(line);
                        stringBuilder.append("\n");
                    } else { // on the line we want to delete
                        i++;
                        z++;
                        continue;
                    }
                    i++;
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

            } catch (IOException io) {
                io.printStackTrace();
            }

        }

        Resources res = getResources();
        list = (ListView) findViewById(R.id.toToListView);

        adapter = new ListAdapter(mainActivity, valuesArr, res);
        list.setAdapter(adapter);
    }

    private void updateFile(String title, String description) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MMMM:yyyy");
        String date = sdf.format(c.getTime());

        sb = new StringBuilder();
        sb.append(title + "///" + description + "///" + date);
        sb.append("\n");

        try {
            outputStream = openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(sb.toString().getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public void onItemClick(int mPosition) {
        list.setItemChecked(mPosition, true);
        return;
    }

    private void makeFile() {
        fileName = "activities.txt";
        con = getBaseContext();
        file = new File(con.getFilesDir(), fileName);
    }
}