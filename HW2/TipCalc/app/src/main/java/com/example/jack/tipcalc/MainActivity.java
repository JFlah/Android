package com.example.jack.tipcalc;

/*
    Flaherty Assignment 2 Part B
 */

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String billAmount = "";
    double total=0;
    double tipAmount=0;
    int tipPercentage=0;

    TextView totalEach;
    TextView tipEach;
    TextView tipPctEach;
    EditText billAmt;
    RadioGroup rGroup;
    SeekBar progBar;
    Spinner numPeople;

    Button roundUp;
    Button reset;

    RadioButton rb10;
    RadioButton rb15;
    RadioButton rb20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalEach = (TextView) findViewById(R.id.totalNumber);
        tipEach = (TextView) findViewById(R.id.tipNumber);
        tipPctEach = (TextView) findViewById(R.id.tipPctNumber);
        rGroup = (RadioGroup) findViewById(R.id.radioGroup);
        progBar = (SeekBar) findViewById(R.id.tipSeek);
        numPeople = (Spinner) findViewById(R.id.numPeopleSpinner);
        roundUp = (Button) findViewById(R.id.roundUp);
        reset = (Button) findViewById(R.id.reset);


        // people dropdown setup
        Spinner dropdown = (Spinner) findViewById(R.id.numPeopleSpinner);
        String[] nums = new String[] {"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nums);
        dropdown.setAdapter(adapter);

        // key listener for bill amount
        billAmt = (EditText) findViewById(R.id.billAmtNumber);
        billAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                billAmount = s.toString();
                if (!billAmount.isEmpty() && !billAmount.equals(".")) {
                    if (!billAmount.equals(".")) {
                        total = Double.parseDouble(billAmount);
                        totalEach.setText("$" + Double.toString(total));
                    }
                } else {
                    total = 0d;
                    totalEach.setText("$0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                billAmount = s.toString();
                if (!billAmount.isEmpty()) {
                    if (!billAmount.equals(".")) {
                        total = Double.parseDouble(billAmount);
                        totalEach.setText("$" + Double.toString(total));
                    }
                } else {
                    total = 0d;
                    totalEach.setText("$0.00");
                }

                // reset stuff every time text is changed
                progBar.setProgress(0);
                tipPctEach.setText("0%");
                tipEach.setText("0.0");
                numPeople.setSelection(0);
            }
        });

        // stuff for radio buttons
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rb10 = (RadioButton) findViewById(R.id.radioButton10);
                rb15 = (RadioButton) findViewById(R.id.radioButton15);
                rb20 = (RadioButton) findViewById(R.id.radioButton20);
                if (rb10.isChecked() || rb10.isFocused()) {
                    rb15.setChecked(false);
                    rb20.setChecked(false);
                    rb10.setChecked(true);
                    if (!billAmt.getText().toString().isEmpty())
                        total = Double.parseDouble(billAmt.getText().toString());
                    else
                        total = 0d;
                    tipPercentage = 10;
                    tipAmount = total * .1;
                    tipEach.setText(Double.toString(tipAmount));
                    total += tipAmount;
                    tipPctEach.setText(Integer.toString(tipPercentage) + "%");
                    totalEach.setText("$" + total);
                }
                if (rb15.isChecked() || rb15.isFocused()) {
                    rb15.setChecked(true);
                    rb20.setChecked(false);
                    rb10.setChecked(false);
                    if (!billAmt.getText().toString().isEmpty())
                        total = Double.parseDouble(billAmt.getText().toString());
                    else
                        total = 0d;
                    tipPercentage = 15;
                    tipAmount = total * .15;
                    tipEach.setText(Double.toString(tipAmount));
                    total += tipAmount;
                    tipPctEach.setText(Integer.toString(tipPercentage) + "%");
                    totalEach.setText("$" + total);
                }
                if (rb20.isChecked() || rb20.isFocused()) {
                    rb15.setChecked(false);
                    rb20.setChecked(true);
                    rb10.setChecked(false);
                    if (!billAmt.getText().toString().isEmpty())
                        total = Double.parseDouble(billAmt.getText().toString());
                    else
                        total = 0d;
                    tipPercentage = 20;
                    tipAmount = total * .2;
                    tipEach.setText(Double.toString(tipAmount));
                    total += tipAmount;
                    tipPctEach.setText(Integer.toString(tipPercentage) + "%");
                    totalEach.setText("$" + Double.toString(total));
                }
                progBar.setProgress(tipPercentage);
                numPeople.setSelection(0);
            }
        });

        // listener for tip seek bar
        progBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!billAmt.getText().toString().isEmpty())
                    total = Double.parseDouble(billAmt.getText().toString());
                else
                    total = 0d;
                tipAmount = 0;
                rGroup.clearCheck();

                tipPercentage = progress;
                tipPctEach.setText(Integer.toString(tipPercentage) + "%");
                billAmount = billAmt.getText().toString();
                tipAmount = total * (tipPercentage / 100.);
                total += tipAmount;

                totalEach.setText("$" + total);
                tipEach.setText(Double.toString(tipAmount));

                numPeople.setSelection(0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // people spinner listener
        numPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int numPeople = Integer.parseInt(parent.getSelectedItem().toString());
                if (!billAmt.getText().toString().isEmpty())
                    total = Double.parseDouble(billAmt.getText().toString());
                else
                    total = 0d;
                total /= numPeople;

                double tempTip = tipAmount;
                tempTip /= numPeople;
                tipEach.setText(Double.toString(tempTip));

                total += tempTip;

                totalEach.setText("$" + Double.toString(total));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // rounding and resetting buttons
        roundUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double tempTotal = total;
                tempTotal = Math.ceil(total);
                totalEach.setText("$" + Double.toString(tempTotal));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAmt.setText("");
                numPeople.setSelection(0);
                tipPctEach.setText("0%");
                recreate();
            }
        });
    }
}