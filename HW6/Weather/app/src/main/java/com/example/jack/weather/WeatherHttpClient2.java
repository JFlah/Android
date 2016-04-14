package com.example.jack.weather;

/**
 * Created by Jack on 4/12/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class WeatherHttpClient2 {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String APIkey = "&APPID=96f70e9c9bdc723bd57b2578ceb0c50e";
    private static String units = "&units=imperial&cnt=3";

    public JSONObject getWeatherData(String location) {
        HttpURLConnection con = null;
        InputStream is = null;
        JSONObject jsonObject;
        try {
            String city =BASE_URL + location + APIkey + units;
            jsonObject = JSONfunctions.getJSONfromURL(city);
            return jsonObject;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;

    }

    public Bitmap getImage(String code) {
        InputStream in = null;
        int resCode = -1;
        HttpURLConnection httpConn = null;
        try {
            URL url = new URL(IMG_URL + code + ".png");
            URLConnection urlConn = url.openConnection();
            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            httpConn = (HttpURLConnection) urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }

            Bitmap bitmap = null;
            bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Throwable t) {
            }
            try {
                httpConn.disconnect();
            } catch (Throwable t) {
            }
        }
        return null;
    }
}
