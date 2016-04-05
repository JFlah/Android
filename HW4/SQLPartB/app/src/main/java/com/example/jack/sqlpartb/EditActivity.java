package com.example.jack.sqlpartb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity {

    EditText titleET;
    EditText descET;
    private ToDo item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent fromIntent = getIntent();
        Bundle extras = fromIntent.getExtras();

        item = (ToDo)extras.getSerializable(MainActivity.TODO_ITEM);

        titleET = (EditText) findViewById(R.id.title);
        descET = (EditText) findViewById(R.id.desc);

        titleET.setText(item.title);
        descET.setText(item.desc);

    }

    public void addItem(View view){
        // Set the activity result and return with finish()
        String title = titleET.getText().toString();
        String desc = descET.getText().toString();

        if(title.isEmpty() || desc.isEmpty()){
            Toast.makeText(EditActivity.this, "ADD ITEM PREASE!", Toast.LENGTH_SHORT).show();
            return;
        }

        item.setTitle(title);
        item.setDesc(desc);

        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_ITEM, item);
        intent.putExtras(bundle);

        setResult(MainActivity.OK_CODE, intent);
        finish();
    }
}


