package com.example.jack.hangman;

/*
    Assignment 1 Part B
    Author: Jack Flaherty
 */

import android.graphics.Color;
import android.graphics.Paint;
//import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static String word;
    public static String currentGuess;
    public static int guessesGiven;
    public static int guessesLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start();
    }

    private void start() {
        setContentView(R.layout.activity_main);
        word = generateWord();
        currentGuess = createDashes(word);
        guessesGiven = word.length()+5;
        guessesLeft = guessesGiven;
        TextView display = (TextView) findViewById(R.id.dashes);
        display.setPaintFlags(display.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        display.setText(currentGuess + "\t consists of " + currentGuess.length() + " letters");
    }

    private String generateWord(){
        String [] words = {"handler","against","horizon","chops","junkyard","amoeba","academy","roast",
                "countryside","children","strange","best","drumbeat","amnesiac","chant","amphibian","smuggler","fetish"};
        Random r = new Random();
        int index = r.nextInt(words.length);
        return words[index];
    }

    private String createDashes(String word) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < word.length()) {
            sb.append("-");
            i++;
        }
        return sb.toString();
    }

    private void check(char c) {
        TextView display = (TextView) findViewById(R.id.dashes);
        guessesLeft--;
        if (word.indexOf(c) != -1) {
            int index = word.indexOf(c);
            char[] currentGuessAsChars = currentGuess.toCharArray();
            currentGuessAsChars[index] = c;
            currentGuess = String.valueOf(currentGuessAsChars);
            while (word.indexOf(c, index + 1) != -1) {
                index = word.indexOf(c, index + 1);
                currentGuessAsChars = currentGuess.toCharArray();
                currentGuessAsChars[index] = c;
                currentGuess = String.valueOf(currentGuessAsChars);
            }
        }
        display.setText(currentGuess + " Used " + Integer.toString(guessesGiven - guessesLeft) + " of " + guessesGiven + " guesses.");
//        Handler handler = new Handler();
        // WIN
        if (currentGuess.equals(word)) {
            display.setText("You WIN! The word was " + word + ". You used " + Integer.toString(guessesGiven - guessesLeft) + " guesses. Click Reset to Play Again!");
            display.setFocusable(false);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    start();
//                }
//            }, 5000);
        }
        // LOSS
        if (guessesLeft <= 0 && !(currentGuess.equals(word))) {
            display.setText("You lost! The word was: " + word + ". You used " + guessesGiven + " guesses. Click Reset to Play Again!");
            display.setFocusable(false);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    start();
//                }
//            }, 5000);
        }
    }

    public void guess(View v) {
        Button clicked;
        switch (v.getId()) {
            case R.id.a:
                clicked = (Button) findViewById(R.id.a);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('a');
                break;
            case R.id.b:
                clicked = (Button) findViewById(R.id.b);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('b');
                break;
            case R.id.c:
                clicked = (Button) findViewById(R.id.c);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('c');
                break;
            case R.id.d:
                clicked = (Button) findViewById(R.id.d);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('d');
                break;
            case R.id.e:
                clicked = (Button) findViewById(R.id.e);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('e');
                break;
            case R.id.f:
                clicked = (Button) findViewById(R.id.f);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('f');
                break;
            case R.id.g:
                clicked = (Button) findViewById(R.id.g);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('g');
                break;
            case R.id.h:
                clicked = (Button) findViewById(R.id.h);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('h');
                break;
            case R.id.i:
                clicked = (Button) findViewById(R.id.i);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('i');
                break;
            case R.id.j:
                clicked = (Button) findViewById(R.id.j);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('j');
                break;
            case R.id.k:
                clicked = (Button) findViewById(R.id.k);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('k');
                break;
            case R.id.l:
                clicked = (Button) findViewById(R.id.l);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('l');
                break;
            case R.id.m:
                clicked = (Button) findViewById(R.id.m);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('m');
                break;
            case R.id.n:
                clicked = (Button) findViewById(R.id.n);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('n');
                break;
            case R.id.o:
                clicked = (Button) findViewById(R.id.o);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('o');
                break;
            case R.id.p:
                clicked = (Button) findViewById(R.id.p);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('p');
                break;
            case R.id.q:
                clicked = (Button) findViewById(R.id.q);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('q');
                break;
            case R.id.r:
                clicked = (Button) findViewById(R.id.r);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('r');
                break;
            case R.id.s:
                clicked = (Button) findViewById(R.id.s);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('s');
                break;
            case R.id.t:
                clicked = (Button) findViewById(R.id.t);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('t');
                break;
            case R.id.u:
                clicked = (Button) findViewById(R.id.u);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('u');
                break;
            case R.id.v:
                clicked = (Button) findViewById(R.id.v);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('v');
                break;
            case R.id.w:
                clicked = (Button) findViewById(R.id.w);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('w');
                break;
            case R.id.x:
                clicked = (Button) findViewById(R.id.x);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('x');
                break;
            case R.id.y:
                clicked = (Button) findViewById(R.id.y);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('y');
                break;
            case R.id.z:
                clicked = (Button) findViewById(R.id.z);
                clicked.setBackgroundColor(Color.RED);
                clicked.setClickable(false);
                check('z');
                break;
            case R.id.reset:
                start();
                break;
        }
    }
}
