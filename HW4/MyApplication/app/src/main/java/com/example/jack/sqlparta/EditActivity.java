package com.example.jack.sqlparta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    EditText titleEdit;
    private ToDo it;
    EditText descEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent fromIntent = getIntent();
        Bundle extras = fromIntent.getExtras();
        it = (ToDo)extras.getSerializable(MainActivity.TODO_ITEM);

        descEdit = (EditText) findViewById(R.id.desc);
        descEdit.setText(it.desc);
        titleEdit = (EditText) findViewById(R.id.title);
        titleEdit.setText(it.title);
    }

    public void addItem(View view){
        String title = titleEdit.getText().toString();
        String desc = descEdit.getText().toString();

        if(title.isEmpty() || desc.isEmpty()){
            Toast.makeText(EditActivity.this, "Must do whole item", Toast.LENGTH_SHORT).show();
            return;
        }
        it.setDesc(desc);
        it.setTitle(title);

        Intent intent = new Intent(this,EditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.TODO_ITEM, it);
        intent.putExtras(bundle);

        setResult(MainActivity.OK_CODE, intent);
        finish();
    }
}

