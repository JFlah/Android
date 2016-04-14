package com.example.jack.weather;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView lv;
    Context context;

    private EditText cityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityText = (EditText) findViewById(R.id.cityText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void startTask(View view) {  //from the button
        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(cityText.getText().toString());
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, List<Weather>> {

        @Override
        protected List<Weather> doInBackground(String... params) {
            List<Weather> weather = new ArrayList<>();
            JSONObject jObj;
            jObj = ((new WeatherHttpClient2()).getWeatherData(params[0]));
            try {
                //call JSON parser to extract data from JSONobject into weather object
                weather = JSONWeatherParser2.getWeather(jObj);

                // Now, let's retrieve the icon bitmap using a seperate call to the weather website
                for (Weather w : weather) {
                    w.iconData = ((new WeatherHttpClient2()).getImage(w.currentCondition.getIcon()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(List<Weather> wea) {
            super.onPostExecute(wea);

            context = getBaseContext();
            lv = (ListView) findViewById(R.id.wListView);
            lv.setAdapter(new CustomAdapter(MainActivity.this, wea));
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent showIntent = new Intent(getBaseContext(), ShowDetail.class);
                    showIntent.putExtra("Position", position);
                    startActivity(showIntent);
                }
            });
        }
    }
}
