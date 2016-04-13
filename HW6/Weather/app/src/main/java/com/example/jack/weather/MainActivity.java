package com.example.jack.weather;

import android.content.Context;
import android.media.session.PlaybackState;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {


    ListView lv;
    Context context;

    //private TextView cityText;
    private Button submit;
    private EditText cityText;
//    private TextView condDescr;
//    private TextView temp;
//    private TextView press;
//    private TextView windSpeed;
//    private TextView windDeg;
//
//    private TextView hum, dateT,day;
//    private ImageView imgView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String city = "Rockport,MA";

        //cityText = (TextView) findViewById(R.id.cityText);
        cityText = (EditText) findViewById(R.id.cityText);
//        condDescr = (TextView) findViewById(R.id.condDescr);
//        temp = (TextView) findViewById(R.id.temp);
//        hum = (TextView) findViewById(R.id.hum);
//        press = (TextView) findViewById(R.id.press);
//        windSpeed = (TextView) findViewById(R.id.windSpeed);
//        windDeg = (TextView) findViewById(R.id.windDeg);
//        imgView = (ImageView) findViewById(R.id.condIcon);
        submit = (Button) findViewById(R.id.button);
//        dateT = (TextView) findViewById(R.id.date);
//        day = (TextView) findViewById(R.id.day);
        //Rockp[ortJSONWeatherTask task = new JSONWeatherTask();
        //task.execute(new String[]{city});
        //task.execute(cityText.getText().toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void startTask(View view) {  //from the button
        JSONWeatherTask task = new JSONWeatherTask();
        //Toast.makeText(MainActivity.this,cityText.getText().toString(),Toast.LENGTH_LONG);
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
            String[] Days = {"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
            long unixSeconds;
            Date date;
            SimpleDateFormat sdf;
            String formattedDate;
            sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
            sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            Calendar cal = Calendar.getInstance();

            context = getBaseContext();
            lv = (ListView) findViewById(R.id.wListView);
            lv.setAdapter(new CustomAdapter(MainActivity.this, wea));

            //System.out.println("LIST LENGTH:" + wea.size());
//            // TODO TESTING
//            Weather weather = wea.get(0);
//
//            unixSeconds = weather.currentCondition.getDate();
//            date = new Date(unixSeconds * 1000L); // *1000 is to convert seconds to milliseconds
//            formattedDate = sdf.format(date);
//            cal.setTimeInMillis(unixSeconds * 1000L);
//            day.setText(Days[cal.get(Calendar.DAY_OF_WEEK) - 1]);
//            dateT.setText(formattedDate);
//
//
//            if (weather.iconData != null && weather.iconData.getByteCount() > 0) {
//                imgView.setImageBitmap(weather.iconData);
//            }
//            condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
//            temp.setText("" + Math.round((weather.temperature.getTemp())) + "°F");
//            hum.setText("" + weather.currentCondition.getHumidity() + "%");
//            press.setText("" + weather.currentCondition.getPressure() + " hPa");
//            windSpeed.setText("" + weather.wind.getSpeed() + " mph");
//            windDeg.setText("" + weather.wind.getDeg() + "\u00b0");

        }
    }
}
