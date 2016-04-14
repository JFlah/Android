package com.example.jack.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowDetail extends AppCompatActivity {

    private TextView condDescr;
    private TextView press;
    private TextView windSpeed;
    private TextView windDeg;
    private TextView tempMax;
    private TextView tempMin;
    private TextView hum;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        condDescr = (TextView) findViewById(R.id.condDescr);
        hum = (TextView) findViewById(R.id.hum);
        press = (TextView) findViewById(R.id.press);
        windSpeed = (TextView) findViewById(R.id.windSpeed);
        windDeg = (TextView) findViewById(R.id.windDeg);
        tempMax = (TextView) findViewById(R.id.tempMax);
        tempMin = (TextView) findViewById(R.id.tempMin);
        backButton = (Button) findViewById(R.id.backButton);

        Intent theIntent = getIntent();
        int position = theIntent.getIntExtra("Position", 5);

        Weather weather = CustomAdapter.weathList.get(position);

        condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
        tempMax.setText("" + weather.temperature.getMaxTemp());
        tempMin.setText("" + weather.temperature.getMinTemp());

        hum.setText("" + weather.currentCondition.getHumidity() + "%");
        press.setText("" + weather.currentCondition.getPressure() + " hPa");
        windSpeed.setText("" + weather.wind.getSpeed() + " mph");
        windDeg.setText("" + weather.wind.getDeg() + "\u00b0");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}