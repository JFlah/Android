package com.example.jack.sqlpartb;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {
    public static final String TABLE_KEY = "main.tableKey";
    private static String LOG_TAG = "BIGGINS";
    public static final String TODO_ITEM = "todoItem";
    public static final String TODO_LIST = "todoList";
    public static final int EDIT_CODE = 100;
    public static final int DELETE_CODE = 200;
    public static final int ADD_CODE = 300;
    public static final int OK_CODE = 400;
    public static final int NOT_OK_CODE = 401;

    private ToDoDataSource datasource;

    private int selected;
    ToDo selectedItem;
    EditText title, desc, time, entry;
    Button mAddButton, mClearButton, mEditButton;
    ListView mListview;
    ArrayAdapter<ToDo> mArrayAdapter;
    ArrayList<ToDo> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);

        mAddButton = (Button) findViewById(R.id.myAddButton);
        mClearButton = (Button) findViewById(R.id.myClearButton);
        mEditButton = (Button) findViewById(R.id.editDeets);

        mListview = getListView();
        mListview.setOnItemClickListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(TABLE_KEY)){
            Intent intent = new Intent(this, TablePickActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String tableName = extras.getString(TABLE_KEY);


        datasource = new ToDoDataSource(this, tableName);
        datasource.open();

        myList = datasource.getAllItems();

        mArrayAdapter = new ArrayAdapter<ToDo>(this,
                android.R.layout.simple_list_item_1, myList);

        setListAdapter(mArrayAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        selected = pos;
        selectedItem = myList.get(pos);
        String n = selectedItem.getTitle();
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")

        ToDo detail = null;
        switch (view.getId()) {
            case R.id.myAddButton:
                String[] comments = new String[] { "Boston", "New York", "Milano",
                        "Firenze", "Roma", "Torino", "Fiesole", "Varazze", "Savona", "Bari", "Venezia",
                        "Chicago" };
                int nextInt = new Random().nextInt(12);

//                //get the intent info
//                // title, desc, time



//                //add the desc and time arguments below
//                detail = datasource.createComment(text.getText().toString());

                mArrayAdapter.add(detail);
                break;
            case R.id.myClearButton:
                if (getListAdapter().getCount() > 0) {
                    detail = (ToDo) getListAdapter().getItem(0);
                    datasource.deleteItem(detail);
                    mArrayAdapter.remove(detail);
                }
                break;
        }
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MA-oAR", "Req " + requestCode + " Res: " + resultCode);
        datasource.open();
        if (resultCode != OK_CODE) {
            return;
        }
        Bundle extras;
        ToDo item;
        switch (requestCode) {
            case ADD_CODE:
                extras = data.getExtras();
                item = (ToDo) extras.getSerializable(MainActivity.TODO_ITEM);
                System.out.println(item);
                if (item == null) break; // Error there
                long rowId = datasource.createTodo(item.title, item.desc, item.time);
                if (rowId != -1)
                    mArrayAdapter.add(item);
                break;
            case EDIT_CODE:
                extras = data.getExtras();
                item = (ToDo) extras.getSerializable(MainActivity.TODO_ITEM);
                datasource.updateTodo(item);
                mArrayAdapter.remove(selectedItem);
                mArrayAdapter.insert(item,selected);
                break;
            case DELETE_CODE:
                extras = data.getExtras();
                ArrayList<ToDo> deleted = (ArrayList<ToDo>) extras.getSerializable(MainActivity.TODO_LIST);
                mArrayAdapter.clear();
                for( ToDo tdItem : deleted)
                    datasource.deleteItem(tdItem);
                mArrayAdapter.addAll(datasource.getAllItems());
                break;
        }
        datasource.close();
    }

    public void editDetailItem(View view) {
        if (selectedItem == null) {
            Toast.makeText(MainActivity.this, "Select an item PLEASE!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TODO_ITEM, selectedItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_CODE);

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_CODE);
    }

    public void deleteItem(View view){
        Intent intent = new Intent(this, DeleteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_LIST,myList);
        intent.putExtras(bundle);
        startActivityForResult(intent, DELETE_CODE);
    }


}



