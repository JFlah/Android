package com.example.jack.weather;

/**
 * Created by Jack on 4/12/2016.
 */
import android.util.Log;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONWeatherParser2 {

    public static List<Weather> getWeather(JSONObject jObj) throws JSONException {
        List<Weather> weath = new ArrayList<>();

        // We create our JSONObject from the data
        //JSONObject jObj = new JSONObject(data);

        JSONArray itemArray = jObj.getJSONArray("list");

        for (int i = 0; i <= 2; i++) {
            Weather weather  = new Weather();
            // We start extracting the info
            JSONObject listItem = itemArray.getJSONObject(i);
            Location loc = new Location();

            weather.currentCondition.setDate(getInt("dt", listItem));

            JSONObject cityObj = getObject("city", jObj);
            JSONObject coordObj = getObject("coord", cityObj);
            loc.setLatitude(getFloat("lat", coordObj));
            loc.setLongitude(getFloat("lon", coordObj));

            //JSONObject sysObj = getObject("sys", jObj);
            loc.setCountry(getString("country", cityObj));
//            loc.setSunrise(getInt("sunrise", sysObj));
//            loc.setSunset(getInt("sunset", sysObj));

            loc.setCity(getString("name", cityObj));
            weather.location = loc;

            // We get weather info (This is an array)
            JSONArray jArr = listItem.getJSONArray("weather");

            // We use only the first value in the array
            JSONObject JSONWeather = jArr.getJSONObject(0);
            weather.currentCondition.setWeatherId(getInt("id", JSONWeather));
            weather.currentCondition.setDescr(getString("description", JSONWeather));
            weather.currentCondition.setCondition(getString("main", JSONWeather));
            weather.currentCondition.setIcon(getString("icon", JSONWeather));

            //JSONObject mainObj = getObject("main", jObj);
            weather.currentCondition.setHumidity(getInt("humidity", listItem));
            weather.currentCondition.setPressure(getInt("pressure", listItem));

            JSONObject tempObj = getObject("temp", listItem);
            weather.temperature.setMaxTemp(getFloat("max", tempObj));
            weather.temperature.setMinTemp(getFloat("min", tempObj));
            //weather.temperature.setTemp(getFloat("temp", tempObj));

            // Wind, always present
//            JSONObject wObj = getObject("wind", jObj);
            weather.wind.setSpeed(getFloat("speed", listItem));
            weather.wind.setDeg(getFloat("deg", listItem));

            // Clouds need try..catch to make sure if no clouds, app just does not die
            try {
                //JSONObject cObj = getObject("clouds", listItem);
                weather.clouds.setPerc(getInt("clouds", listItem));
            } catch (JSONException j) {
                Log.e("No Clouds", j.toString());
            }
            //Rain need try..catch to make sure if no rain, app just does not die
//            try {
//                JSONObject rObj = getObject("rain", jObj);
//                weather.rain.setAmmount(getFloat("all", rObj));
//            } catch (JSONException j) {
//                Log.e("No Rain", j.toString());
//            }

//            //Snow need try..catch to make sure if no snow, app just does not die
//            try {
//                JSONObject sObj = getObject("snow", jObj);
//                weather.snow.setAmmount(getFloat("all", sObj));
//            } catch (JSONException j) {
//                Log.e("No Snow", j.toString());
//            }

            weath.add(weather);
            //System.out.println("ADDED ONE!");
        }
        // We download the icon to show

        return weath;
    }


    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
