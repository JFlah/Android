package com.example.jack.sqlpartb;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class TablePickActivity extends ListActivity implements AdapterView.OnItemClickListener {
    Button mAddButton;
    ListView mListview;
    ArrayAdapter<String> mArrayAdapter;
    ArrayList<String> myList;
    EditText mCreateListET;
    ToDoDataSource datasource;
    public static final String TABLES_TABLE = "table.table.table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_pick);
        mListview = getListView();
        mListview.setOnItemClickListener(this);

        datasource = new ToDoDataSource(this, TABLES_TABLE);
        datasource.open();

        mAddButton = (Button) findViewById(R.id.myAddButton);
        mCreateListET = (EditText) findViewById(R.id.editText1);
        ArrayList<ToDo> items = datasource.getAllItems();
        myList = new ArrayList<>();
        for (ToDo td : items){
            myList.add(td.getTitle());
        }

        mArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, myList);
        setListAdapter(mArrayAdapter);
    }

    public void createList(View view) {
        String listName = mCreateListET.getText().toString();

        if(listName.isEmpty())
            return;

        datasource.createTodo(listName, listName, new Date().toString());
        goToList(listName);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        String tbl = myList.get(pos);
        goToList(tbl);
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();

        ArrayList<ToDo> items = datasource.getAllItems();
        myList.clear();
        for (ToDo td : items){
            myList.add(td.getTitle());
        }
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

    private void goToList(String table){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.TABLE_KEY, table);
        startActivity(intent);
    }
}




