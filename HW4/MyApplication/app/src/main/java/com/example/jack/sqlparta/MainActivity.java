package com.example.jack.sqlparta;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    public static final int EDIT_CODE = 100;
    public static final int ADD_CODE = 101;
    public static final String TODO_ITEM = "todoItem";
    public static final String TODO_LIST = "todoList";
    public static final int OK_CODE = 102;
    public static final int DELETE_CODE = 103;
    public static final int NOT_OK_CODE = 104;

    private ToDoDataSource datasource;

    private int selected;
    ToDo selectedItem;
    EditText title, desc;
    Button mAddButton;
    Button mClearButton;
    Button mEditButton;
    ListView lv;
    ArrayAdapter<ToDo> aa;
    ArrayList<ToDo> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting activity things from view
        mAddButton = (Button) findViewById(R.id.myAddButton);
        mClearButton = (Button) findViewById(R.id.myClearButton);
        mEditButton = (Button) findViewById(R.id.editDetails);
        title = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        lv = getListView();
        lv.setOnItemClickListener(this);

        // open db stuff and get items
        datasource = new ToDoDataSource(this);
        datasource.open();
        myList = datasource.getAllItems();

        // set adapters
        aa = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, myList);
        setListAdapter(aa);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        selected = pos;
        selectedItem = myList.get(pos);
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
        datasource.open();
        if (resultCode != OK_CODE) {
            return;
        }
        Bundle extras;
        ToDo item;
        switch (requestCode) {
            case EDIT_CODE:
                extras = data.getExtras();
                item = (ToDo) extras.getSerializable(MainActivity.TODO_ITEM);
                datasource.updateTodo(item);
                aa.remove(selectedItem);
                aa.insert(item,selected);
                break;
            case ADD_CODE:
                extras = data.getExtras();
                item = (ToDo) extras.getSerializable(MainActivity.TODO_ITEM);
                System.out.println(item);
                if (item == null) break;
                long rowId = datasource.createTodo(item.title, item.desc, item.time);
                if (rowId != -1) {
                    aa.add(item);
                }
            break;
            case DELETE_CODE:
                extras = data.getExtras();
                ArrayList<ToDo> deleted = (ArrayList<ToDo>) extras.getSerializable(MainActivity.TODO_LIST);
                aa.clear();
                for( ToDo tdItem : deleted)
                    datasource.deleteItem(tdItem);
                aa.addAll(datasource.getAllItems());
                break;
        }
        datasource.close();
    }

    public void editDetailItem(View view) {
        if (selectedItem == null) {
            Toast.makeText(MainActivity.this, "Pick an item", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TODO_ITEM, selectedItem);
        intent.putExtras(bundle);
        startActivityForResult(intent, EDIT_CODE);
    }

    public void deleteItem(View view){
        Intent intent = new Intent(this, DeleteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_LIST,myList);
        intent.putExtras(bundle);
        startActivityForResult(intent, DELETE_CODE);
    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, ADD_CODE);
    }
}