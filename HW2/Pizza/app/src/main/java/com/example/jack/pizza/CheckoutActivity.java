package com.example.jack.pizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    Button order;

    double total = 0;
    String pizzaSize = "";
    String name = "";
    ArrayList<String> veggieList = new ArrayList<>();
    ArrayList<String> meatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Bundle extras = getIntent().getExtras();

        pizzaSize = extras.getString("Size");
        name = extras.getString("Name");
        veggieList = extras.getStringArrayList("Veggies");
        meatList = extras.getStringArrayList("Meats");

        TextView order;
        doOrder();

        order = (Button) findViewById(R.id.orderButton);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Thank you for your order", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    protected void doOrder() {
        switch (pizzaSize) {
            case "Small":
                total += 5;
                if (!veggieList.contains("None"))
                    total += veggieList.size();
                if (!meatList.contains("None"))
                    total += meatList.size()*2;
                break;
            case "Medium":
                total += 7;
                if (!veggieList.contains("None"))
                    total += veggieList.size()*2;
                if (!meatList.contains("None"))
                    total += meatList.size()*4;
                break;
            case "Large":
                total += 10;
                if (!veggieList.contains("None"))
                    total += veggieList.size()*3;
                if (!meatList.contains("None"))
                    total += meatList.size()*6;
                break;
        }
        TextView orderView = (TextView) findViewById(R.id.orderView);
        TextView totalView = (TextView) findViewById(R.id.totalView);

        StringBuilder vegString = new StringBuilder();
        StringBuilder meatString = new StringBuilder();
        for (String veg : veggieList) {
            vegString.append(veg + " ");
        }
        for (String meat : meatList) {
            meatString.append(meat + " ");
        }


        totalView.setText("Your total is: $" + Double.toString(total));
        orderView.setText("Name: " + name + "\nSize: " + pizzaSize +
                "\nVeggies: " + vegString.toString() + "\nMeats: " + meatString.toString());
    }
}
