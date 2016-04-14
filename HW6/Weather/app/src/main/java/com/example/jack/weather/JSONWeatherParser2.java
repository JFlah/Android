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

            loc.setCountry(getString("country", cityObj));

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

            weather.currentCondition.setHumidity(getInt("humidity", listItem));
            weather.currentCondition.setPressure(getInt("pressure", listItem));

            JSONObject tempObj = getObject("temp", listItem);
            weather.temperature.setMaxTemp(getFloat("max", tempObj));
            weather.temperature.setMinTemp(getFloat("min", tempObj));

            // Wind, always present
            weather.wind.setSpeed(getFloat("speed", listItem));
            weather.wind.setDeg(getFloat("deg", listItem));

            // Clouds need try..catch to make sure if no clouds, app just does not die
            try {
                weather.clouds.setPerc(getInt("clouds", listItem));
            } catch (JSONException j) {
                Log.e("No Clouds", j.toString());
            }
            weath.add(weather);
        }
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