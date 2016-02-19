package com.example.jack.pizza;

/*
    Flaherty Assignment 2 Part A
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button size;
    Button veg;
    Button meat;
    Button checkOut;

    String sizeResult = "";
    String name = "";
    ArrayList<String> vegList = new ArrayList<>();
    ArrayList<String> meatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set home image
        ImageView image = (ImageView) findViewById(R.id.homeImage);
        image.setImageResource(R.mipmap.pizza2);

        // get buttons
        size = (Button) findViewById(R.id.sizeButton);
        veg = (Button) findViewById(R.id.veggieButton);
        meat = (Button) findViewById(R.id.meatButton);
        checkOut = (Button) findViewById(R.id.checkoutButton);

        // set listeners
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SizeActivity.class);
                startActivityForResult(intent, 001);
            }
        });
        veg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), VegActivity.class);
                startActivityForResult(intent, 002);
            }
        });
        meat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MeatActivity.class);
                startActivityForResult(intent, 003);
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vegList.isEmpty())
                    vegList.add("None");
                if (meatList.isEmpty())
                    meatList.add("None");

                TextView error = (TextView) findViewById(R.id.error);
                EditText nameText = (EditText) findViewById(R.id.nameView);
                name = nameText.getText().toString();

                if (!sizeResult.isEmpty() && !name.isEmpty()) {
                    error.setText("");
                    Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                    intent.putExtra("Size", sizeResult);
                    intent.putExtra("Name", name);
                    intent.putStringArrayListExtra("Veggies", vegList);
                    intent.putStringArrayListExtra("Meats", meatList);
                    startActivity(intent);
                } else {
                    if (name.isEmpty() && sizeResult.isEmpty())
                        error.setText("Please select a size and enter name before placing your order.");
                    else if (name.isEmpty() && !sizeResult.isEmpty())
                        error.setText("Please enter name before placing your order.");
                    else if (!name.isEmpty() && sizeResult.isEmpty())
                        error.setText("Please select a size before placing your order.");
                }
            }
        });

    }

    // activity results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView sizeView;
        TextView vegView;
        TextView meatView;

        String result;
        ArrayList<String> listResult;

        switch (requestCode) {
            // size
            case 001:
                if (resultCode == Activity.RESULT_OK) {
                    result = data.getStringExtra("Size");
                    sizeResult = result;
                    sizeView = (TextView) findViewById(R.id.sizeView);
                    sizeView.setText("Size: " + sizeResult);
                }
                break;
            // veg
            case 002:
                if (resultCode == Activity.RESULT_OK) {
                    listResult = data.getStringArrayListExtra("Veggies");
                    vegList = listResult;
                    vegView = (TextView) findViewById(R.id.vegView);
                    StringBuilder vegString = new StringBuilder();
                    for (String veg : vegList) {
                        vegString.append(veg + " ");
                    }
                    vegView.setText("Veggies: " + vegString);
                }
                break;
            // meat
            case 003:
                if (resultCode == Activity.RESULT_OK) {
                    listResult = data.getStringArrayListExtra("Meats");
                    meatList = listResult;
                    meatView = (TextView) findViewById(R.id.meatView);
                    StringBuilder meatString = new StringBuilder();
                    for (String meat : meatList) {
                        meatString.append(meat + " ");
                    }
                    meatView.setText("Meats: " + meatString);
                    break;
                }
        }

    }
}
