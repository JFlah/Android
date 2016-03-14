package com.example.jack.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

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

//    Button showDetail;
//    Button editDetail;

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

//        showDetail = (Button) findViewById(R.id.show_detail);
//        editDetail = (Button) findViewById(R.id.edit_detail);

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

//                System.out.println(toDeleteArray.toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        //System.out.println("delete");
                        deleteSelectedLines(toDeleteArray);
                        //System.out.println("done del");
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
                    //System.out.println("Title: " + toDo.getTitle());
                    //System.out.println("Description: " + toDo.getDescription());
                    //System.out.println("Date: " + toDo.getDate());
                    Toast.makeText(getBaseContext(), "New Item Added!", Toast.LENGTH_SHORT).show();
                    //System.out.println("Adding it!");
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    public void deleteSelectedLines(ArrayList<Integer> linesToDelete) {
//        for (int line : linesToDelete) {
//            //System.out.println("line to delete: " + line);
//        }
        String fileName = "activities.txt";
        //StringBuilder sb = new StringBuilder();
        Context con = getBaseContext();


        // READING FROM FILE, DELETING CORRECT ONES
        int z = 0;
        Collections.sort(linesToDelete);
        //System.out.println("sorted list: " + linesToDelete.toString());
        for (int position : linesToDelete) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean oneDone = false;
            //linesToDelete.remove((Object) position);
//            for (ToDo todo : valuesArr) {
//                System.out.println("Values arr: " + todo.getTitle());
//            }
//            System.out.println("position to remove: " + position);
//            System.out.println("current z: " + z);
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
//                        System.out.println("here");
//                        System.out.println("List: ");
                        stringBuilder.append(line);
                        stringBuilder.append("\n");
//                        System.out.println(stringBuilder.toString());
                    } else { // on the line we want to delete
//                        System.out.println("on line to delete");
                        i++;
                        z++;
                        continue;
                    }
                    i++;
                    //z++;
//                    System.out.println("current sb: " + stringBuilder.toString());
                }
                // overwrite whole file with new SB
                try {
//                    System.out.println("reading new sb into file: " + stringBuilder.toString());
                    OutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                    outputStream.write(stringBuilder.toString().getBytes());
                    outputStream.close();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException io) {
                    io.printStackTrace();
                }

//                System.out.println("sb after done looping: " + stringBuilder.toString());
//                for (ToDo todo : valuesArr) {
//                    System.out.println("Values arr: " + todo.getTitle());
//                }
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
//        System.out.println("Making file:");
        fileName = "activities.txt";
        con = getBaseContext();
        file = new File(con.getFilesDir(), fileName);
    }
}
