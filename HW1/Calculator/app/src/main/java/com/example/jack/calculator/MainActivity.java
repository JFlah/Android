package com.example.jack.calculator;

/*
    Assignment 1 Part A
    Author: Jack Flaherty
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView display;
    public String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView) findViewById(R.id.display);
    }
    
    public void clickBtn(View v) {
        switch (v.getId()) {
            case R.id.delete:
                delete();
                break;
            case R.id.sevenButton:
                insert(7);
                break;
            case R.id.eightButton:
                insert(8);
                break;
            case R.id.nineButton:
                insert(9);
                break;
            case R.id.divideButton:
                insertOp('/');
                break;
            case R.id.fourButton:
                insert(4);
                break;
            case R.id.fiveButton:
                insert(5);
                break;
            case R.id.sixButton:
                insert(6);
                break;
            case R.id.multiplyButton:
                insertOp('x');
                break;
            case R.id.oneButton:
                insert(1);
                break;
            case R.id.twoButton:
                insert(2);
                break;
            case R.id.threeButton:
                insert(3);
                break;
            case R.id.minusButton:
                insertOp('-');
                break;
            case R.id.pointButton:
                insertOp('.');
                break;
            case R.id.zeroButton:
                insert(0);
                break;
            case R.id.equalsButton:
                reset();
                break;
            case R.id.plusButton:
                insertOp('+');
                break;
        }
    }

    private void insert(int i) {
        str += Integer.toString(i) + " ";
        display.setText(str);
    }

    private void insertOp(char c) {
        str += c + " ";
        display.setText(str);
    }

    private void delete() {
        if (str.length() >= 2) {
            str = str.substring(0, str.length()-2);
        }
        if (str.length() == 1) {
            str = str.substring(0, str.length()-1);
        }
        display.setText(str);
    }

    private void reset() {
        str = "";
        display.setText(str);
    }

}
