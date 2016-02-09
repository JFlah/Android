package com.example.jack.calccomplete;

/*
    Assignment 1 Part C
    Author: Jack Flaherty
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    TextView display;
    public String str = "";
    public boolean dotAlready = false;

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
            case R.id.clear:
                clear();
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
                insertOp('*');
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
                doCalc();
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
        if (str.length() > 1) {
            if (str.charAt(str.length() - 2) != '/' && str.charAt(str.length() - 2) != '*'
                    && str.charAt(str.length() - 2) != '-' && str.charAt(str.length() - 2) != '+' && c != '.') {
                str += c + " ";
                display.setText(str);
                dotAlready = false;
            }
        }
        if (c == '.' && !dotAlready) {
            if (str.length() > 1) {
                if (str.charAt(str.length() - 2) != '.') {
                    str += c + " ";
                    display.setText(str);
                    dotAlready = true;
                }
            } else {
                str += c + " ";
                display.setText(str);
                dotAlready = true;
            }
        }
    }

    private void delete() {
        if (str.length() >= 2) {
            if (str.charAt(str.length() - 2) == '.') {
                dotAlready = false;
            }
            str = str.substring(0, str.length() - 2);
        }
        if (str.length() == 1) {
            if (str.charAt(str.length() - 1) == '.') {
                dotAlready = false;
            }
            str = str.substring(0, str.length() - 1);
        }
        display.setText(str);
    }

    private void clear() {
        str="";
        display.setText(str);
    }

    private void doCalc() {
        Queue<Float> nums = new LinkedList<>();
        Queue<String> ops = new LinkedList<>();

        StringBuilder currentNum = new StringBuilder();

        String equation = display.getText().toString();
        String delims = "[ ]+";
        String[] tokens = equation.split(delims);

        // create our list of nums and ops
        for (int i = 0; i < tokens.length; i++) {
            if (!isOp(tokens[i])) {
                currentNum.append(tokens[i]);
            } else { // isOp
                nums.add(Float.parseFloat(currentNum.toString()));
                ops.add(tokens[i]);
                currentNum.delete(0, currentNum.length());
            }
        }
        // catch the final number and add it (if there is one)
        if (currentNum.length() != 0) {
            nums.add(Float.parseFloat(currentNum.toString()));
        }

        // Check to see if there's an extra operator and fix it

        if (ops.size() == nums.size()) {
            Queue<String> ops2 = new LinkedList<>();
            int length = ops.size();
            for (int i = 0; i < length - 1; i++) {
                String cur = ops.remove();
                ops2.add(cur);
            }
            ops = ops2;
        }

        // I now have a queue of nums and the queue of ops

        Float result = 0f;

        // If they hit = with no second number, default to the first
        if (nums.size() == 1) {
            display.setText(nums.remove().toString());
        }

        // start calc'ing
        int size = ops.size();
        for (int i = 0; i < size; i++) {
            Float first = 0f, second = 0f;
            Float next = 0f;
            switch (ops.remove()) {
                case "+":
                    if (i == 0) {
                        first = nums.remove();
                        second = nums.remove();
                        result += first + second;
                    } else {
                        next = nums.remove();
                        result += next;
                    }
                    break;
                case "-":
                    if (i == 0) {
                        first = nums.remove();
                        second = nums.remove();
                        result += first - second;
                    } else {
                        next = nums.remove();
                        result -= next;
                    }
                    break;
                case "/":
                    if (i == 0) {
                        first = nums.remove();
                        second = nums.remove();
                        result += first / second;
                    } else {
                        next = nums.remove();
                        result /= next;
                    }
                    break;
                case "*":
                    if (i == 0) {
                        first = nums.remove();
                        second = nums.remove();
                        result += first * second;
                    } else {
                        next = nums.remove();
                        result *= next;
                    }
                    break;
            }
        }
        display.setText(result.toString());
    }

    // determine if the current token is an operator
    private boolean isOp(String s) {
        if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("*")) {
            return true;
        }
        return false;
    }
}
