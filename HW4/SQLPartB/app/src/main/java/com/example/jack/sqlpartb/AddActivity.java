package com.example.jack.sqlpartb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    EditText titleET;
    EditText descET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleET = (EditText) findViewById(R.id.title);
        descET = (EditText) findViewById(R.id.desc);
    }

    public void addItem(View view){
        String title = titleET.getText().toString();
        String desc = descET.getText().toString();
        if(title.isEmpty() || desc.isEmpty()){
            Toast.makeText(AddActivity.this, "Must do a whole item", Toast.LENGTH_SHORT).show();
            return;
        }

        ToDo item = new ToDo(title,desc);

        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_ITEM, item);
        intent.putExtras(bundle);

        setResult(MainActivity.OK_CODE, intent);
        finish();
    }
}



