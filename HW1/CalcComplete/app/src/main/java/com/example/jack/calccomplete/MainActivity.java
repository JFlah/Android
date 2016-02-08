package com.example.jack.calccomplete;

/*
    Assignment 1 Part C
    Author: Jack Flaherty
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashSet;

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
        if (str.length() > 0) {
            if (str.charAt(str.length() - 1) != '/' && str.charAt(str.length() - 1) != '*'
                    && str.charAt(str.length() - 1) != '-' && str.charAt(str.length() - 1) != '+' && c != '.') {
                str += c + " ";
                display.setText(str);
            }
        }
        if (c == '.') {
            if (str.length() > 0) {
                if (str.charAt(str.length() - 1) != '.') {
                    str += c + " ";
                    display.setText(str);
                }
            } else {
                str += c + " ";
                display.setText(str);
            }
        }
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

    private void doCalc() {
        String equation = display.getText().toString();
        double answer = eval(equation);

        display.setText(Double.toString(answer));
    }

    public static double eval(final String str) {
        class Parser {
            int pos = -1, c;

            void eatChar() {
                c = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            void eatSpace() {
                while (Character.isWhitespace(c)) eatChar();
            }

            double parse() {
                eatChar();
                double v = parseExpression();
                if (c != -1) throw new RuntimeException("Unexpected: " + (char)c);
                return v;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor | term brackets
            // factor = brackets | number | factor `^` factor
            // brackets = `(` expression `)`

            double parseExpression() {
                double v = parseTerm();
                for (;;) {
                    eatSpace();
                    if (c == '+') { // addition
                        eatChar();
                        v += parseTerm();
                    } else if (c == '-') { // subtraction
                        eatChar();
                        v -= parseTerm();
                    } else {
                        return v;
                    }
                }
            }

            double parseTerm() {
                double v = parseFactor();
                for (;;) {
                    eatSpace();
                    if (c == '/') { // division
                        eatChar();
                        v /= parseFactor();
                    } else if (c == '*' || c == '(') { // multiplication
                        if (c == '*') eatChar();
                        v *= parseFactor();
                    } else {
                        return v;
                    }
                }
            }

            double parseFactor() {
                double v;
                boolean negate = false;
                eatSpace();
                if (c == '+' || c == '-') { // unary plus & minus
                    negate = c == '-';
                    eatChar();
                    eatSpace();
                }
                if (c == '(') { // brackets
                    eatChar();
                    v = parseExpression();
                    if (c == ')') eatChar();
                } else { // numbers
                    StringBuilder sb = new StringBuilder();
                    while ((c >= '0' && c <= '9') || c == '.') {
                        sb.append((char)c);
                        eatChar();
                    }
                    if (sb.length() == 0) throw new RuntimeException("Unexpected: " + (char)c);
                    v = Double.parseDouble(sb.toString());
                }
                eatSpace();
                if (c == '^') { // exponentiation
                    eatChar();
                    v = Math.pow(v, parseFactor());
                }
                if (negate) v = -v; // unary minus is applied after exponentiation; e.g. -3^2=-9
                return v;
            }
        }
        return new Parser().parse();
    }

//    private boolean isOp(String s, int element) {
//        HashSet<Integer> hs = new HashSet<>();
//        if (s.equals("+") || s.equals("-") || s.equals("/") || s.equals("x")) {
//            hs.add(element);
//            return true;
//        }
//        return false;
//    }

//    private void reset() {
//        str = "";
//        display.setText(str);
//    }

}
