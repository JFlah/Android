package com.example.jack.scrapept2;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private CityDataSource dataSource;

    Button largestIncrButton;
    Button smallestIncrButton;
    Button popUnder5000Button;

    TextView increase;
    TextView decrease;
    Spinner spin;

    String url;
    ArrayList<City> cityList = new ArrayList<City>();

    private Pattern tPattern = null;
    private String   tRE = "\\>(\\d+\\,\\d+)?"; //regular expression

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spin = (Spinner) findViewById(R.id.spin);

        largestIncrButton = (Button) findViewById(R.id.increaseButton);
        smallestIncrButton = (Button) findViewById(R.id.decreaseButton);
        popUnder5000Button = (Button) findViewById(R.id.citiesUnder5kButton);

        increase = (TextView) findViewById(R.id.increase);
        decrease = (TextView) findViewById(R.id.decrease);

        url = "https://malegislature.gov/District/CensusData";

        doScrape scrapeTask = new doScrape();
        scrapeTask.execute(url);

    }

    @Override
    protected void onResume() {
        //dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //dataSource.close();
        super.onPause();
    }

    //this approach is more powerfull than a straight read of the url
    //since it can interact with the the browser
    private InputStream openHttpConnection(String urlStr) {
        InputStream in = null;
        int resCode = -1;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            if (!(urlConn instanceof HttpURLConnection)) {
                throw new IOException("URL is not an Http URL");
            }
            HttpURLConnection httpConn = urlConn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return in;
    }

    private class doScrape extends AsyncTask<String, String, String> {

        // Executed on main UI thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            /*
            ConnectivityManager Class that answers queries about the state of network connectivity.
            It also notifies applications when network connectivity changes.

            The getActiveNetworkInfo() method of ConnectivityManager returns a NetworkInfo instance
            representing the first connected network interface it can find or null if none if the
            interfaces are connected. Checking if this method returns null should be enough to tell
            if an internet connection is available.
             */

            if (networkInfo != null && networkInfo.isConnected()) {
                url = "https://malegislature.gov/District/CensusData";
            }
        }

        // Run on background thread.
        @Override
        protected String doInBackground(String... arguments) {
            // Extract arguments
            String urlIn = arguments[0];
            String s="",ss="";
            BufferedReader in = null;
            InputStream ins = null;

            if (tPattern == null)
                tPattern = Pattern.compile(tRE); // slow, only do once, and not on UI thread
            try {
                ins = openHttpConnection(urlIn);
                in = new BufferedReader(new InputStreamReader(ins));

                String inputLine;
                String curr_city = "";
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("row") ) {//find city
                        String cityLine = inputLine.toString().substring(48, inputLine.toString().length()-5);
                        if (cityLine.charAt(cityLine.length()-1) != ' ') {
                            curr_city = cityLine;
                        }

                        inputLine = in.readLine(); //skip a line
                        inputLine = in.readLine(); //get old population data

                        Matcher m = tPattern.matcher(inputLine);
                        if (m.find()) {
                            s = m.group(1);
                        }
                        inputLine = in.readLine(); //skip a line
                        inputLine = in.readLine(); //get new population data

                        Matcher mm = tPattern.matcher(inputLine);
                        if (mm.find()) {
                            ss = mm.group(1);
                        }
                        // get pop change
                        inputLine = in.readLine(); //skip a line
                        inputLine = in.readLine(); //get pop change data
                        String popChange = inputLine.toString().substring(51, inputLine.toString().length()-5);
                        //System.out.println(popChange);

                        boolean add = true;
                        for (City c : cityList) {
                            if (c.getName().equals(curr_city)) {
                                add = false;
                            }
                        }
                        if (add) {
                            cityList.add(new City(curr_city, s, ss, popChange));
                        }
                    }
                }
            } catch (IOException e) {
                Log.e("ScrapeTemperatures", "Unable to open url: " + url);
            } catch (Exception e) {
                Log.e("ScrapeTemperatures", e.toString());
            } finally {
                if (in != null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        // ignore, we tried and failed to close, limp along anyway
                    }
            }
            return "";
        }

        // Executed on main UI thread.
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            String message = values[0];
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] parts = result.split("\\;");
            String oldp = parts[0];

            TextView wait = (TextView) findViewById(R.id.waitText);
            wait.setText("Good to Go!");


            // db stuff
            dataSource = new CityDataSource(MainActivity.this);
            dataSource.open();

            if (dataSource.getAllItems().size() == 0) {
                dataSource.create();
            } else {
                dataSource.delete();
                dataSource.create();
            }

//            System.out.println(cityList.size() + "SIZE");

            // put list into db
            for (City cityToAdd : cityList) {
                if (cityToAdd.getOldPop() == null) {
                    cityToAdd.setOldPop("1000");
                }
                if (cityToAdd.getNewPop() == null) {
                    cityToAdd.setNewPop("1000");
                }
                long rowId = dataSource.createCity(cityToAdd.getName(), cityToAdd.getOldPop(), cityToAdd.getNewPop(), cityToAdd.getPopChange());
                //System.out.println("Row id: " + rowId);
            }

            spin = (Spinner) findViewById(R.id.spin);

            largestIncrButton = (Button) findViewById(R.id.increaseButton);
            smallestIncrButton = (Button) findViewById(R.id.decreaseButton);
            popUnder5000Button = (Button) findViewById(R.id.citiesUnder5kButton);

            increase = (TextView) findViewById(R.id.increase);
            decrease = (TextView) findViewById(R.id.decrease);

            largestIncrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = dataSource.database.query(MySQLiteHelper.CityEntry.TABLE_CITY,
                            dataSource.allColumns, null, null, null, null, null);

                    cursor.moveToFirst();
                    int largestIncr = -99999;
                    String theCity = "";
                    while (!cursor.isAfterLast()) {
                        City city = dataSource.cursorToItem(cursor);
                        String popChange = city.getPopChange();
                        char[] pop = popChange.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        for (char c : pop) {
                            if (c != ',')
                                sb.append(c);
                        }
                        if (Integer.parseInt(sb.toString()) > largestIncr) {
                            largestIncr = Integer.parseInt(sb.toString());
                            theCity = city.getName();
                        }
                        cursor.moveToNext();
                    }
                    // closing
                    cursor.close();
                    increase.setText("City: " + theCity + "\nPop. Change: " + Integer.toString(largestIncr));

                }
            });
            smallestIncrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = dataSource.database.query(MySQLiteHelper.CityEntry.TABLE_CITY,
                            dataSource.allColumns, null, null, null, null, null);

                    cursor.moveToFirst();
                    int largestDecr = 99999;
                    String theCity = "";
                    while (!cursor.isAfterLast()) {
                        City city = dataSource.cursorToItem(cursor);
                        String popChange = city.getPopChange();
                        char[] pop = popChange.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        for (char c : pop) {
                            if (c != ',')
                                sb.append(c);
                        }
                        if (Integer.parseInt(sb.toString()) < largestDecr) {
                            largestDecr = Integer.parseInt(sb.toString());
                            theCity = city.getName();
                        }
                        cursor.moveToNext();
                    }
                    // closing
                    cursor.close();
                    decrease.setText("City: " + theCity + "\nPop. Change: " + Integer.toString(largestDecr));
                }
            });
            popUnder5000Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<City> under5000Cities = new ArrayList<City>();

                    Cursor cursor = dataSource.database.query(MySQLiteHelper.CityEntry.TABLE_CITY,
                            dataSource.allColumns, null, null, null, null, null);
                    cursor.moveToFirst();
                    int largestDecr = 99999;
                    String theCity = "";
                    while (!cursor.isAfterLast()) {
                        City city = dataSource.cursorToItem(cursor);
                        String currPop = city.getNewPop();
                        char[] pop = currPop.toCharArray();
                        StringBuilder sb = new StringBuilder();
                        for (char c : pop) {
                            if (c != ',')
                                sb.append(c);
                        }
                        if (Integer.parseInt(sb.toString()) < 5000) {
                            under5000Cities.add(city);
                        }
                        cursor.moveToNext();
                    }
                    // closing
                    cursor.close();

                    ArrayAdapter<City> aa = new ArrayAdapter<City>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, under5000Cities);
                    spin.setAdapter(aa);
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}

