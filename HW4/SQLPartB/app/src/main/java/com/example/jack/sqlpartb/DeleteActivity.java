package com.example.jack.sqlpartb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DeleteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<ToDo> myList;
    ListView mListView;
    ArrayAdapter<ToDo> mArrayAdapter;
    ArrayList<ToDo> deleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent from = getIntent();
        Bundle extras = from.getExtras();
        myList = (ArrayList<ToDo>) extras.getSerializable(MainActivity.TODO_LIST);
        deleted = new ArrayList<>();

        mListView = (ListView) findViewById(R.id.myListView);
        mArrayAdapter = new ArrayAdapter<ToDo>(this, android.R.layout.simple_list_item_1, myList);
        mListView.setAdapter(mArrayAdapter);
        mListView.setOnItemClickListener(this);
    }

    public void done(View view){
        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_LIST, deleted);
        intent.putExtras(bundle);

        setResult(MainActivity.OK_CODE,intent);
        finish();
    }

    public void cancel(View view){
        setResult(MainActivity.NOT_OK_CODE);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToDo removedItem = myList.remove(position);
        mArrayAdapter.notifyDataSetChanged();
        deleted.add(removedItem);
    }
}
