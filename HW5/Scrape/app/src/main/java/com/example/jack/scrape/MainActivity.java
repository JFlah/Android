package com.example.jack.scrape;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    int count;
    Spinner spin;
    String url;
    ArrayList<City> cityList = new ArrayList<City>();
    TextView cName;// = (TextView) findViewById(R.id.cityName);
    TextView cOldPop;// = (TextView) findViewById(R.id.oldPop);
    TextView cNewPop;// = (TextView) findViewById(R.id.newPop);

    private Pattern tPattern = null;
    private String   tRE = "\\>(\\d+\\,\\d+)?"; //regular expression

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        cName = (TextView) findViewById(R.id.cityName);
        cOldPop = (TextView) findViewById(R.id.oldPop);
        cNewPop = (TextView) findViewById(R.id.newPop);

        count = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spin = (Spinner) findViewById(R.id.spin);
        url = "https://malegislature.gov/District/CensusData";

        doScrape scrapeTask = new doScrape();
        scrapeTask.execute(url);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City selectedC = (City) spin.getItemAtPosition(position);
//                System.out.println(selectedC.toString());
//                System.out.println(selectedC.getOldPop());
//                System.out.println(selectedC.getNewPop());

                cName = (TextView) findViewById(R.id.cityName);
                cOldPop = (TextView) findViewById(R.id.oldPop);
                cNewPop = (TextView) findViewById(R.id.newPop);

                cName.setText(selectedC.toString());
                if (selectedC.getOldPop() == null) {
                    cOldPop.setText("<1000");
                } else {
                    cOldPop.setText(selectedC.getOldPop());
                }
                if (selectedC.getNewPop() == null) {
                    cNewPop.setText("<1000");
                } else {
                    cNewPop.setText(selectedC.getNewPop());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
            //progressBar.setVisibility(View.VISIBLE);

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
                //url =  "http://www.citytowninfo.com/places/massachusetts";
                //url = "http://en.wikipedia.org/wiki/List_of_municipalities_in_Massachusetts";
            } //else {
//                city.setText(getString(R.string.noNetworkError));
//                try {
//                    // thread to sleep for 100 milliseconds
//                    Thread.sleep(100);
//                } catch (Exception e) {
//                    System.out.println(e);
//                }
//                //onDestroy();
//                onStop();
//            }

        }

        // Run on background thread.
        @Override
        protected String doInBackground(String... arguments) {
            // Extract arguments
            String urlIn = arguments[0];
            //String city = arguments[1];
            String s="",ss="";
            BufferedReader in = null;
            InputStream ins = null;

            if (tPattern == null)
                tPattern = Pattern.compile(tRE); // slow, only do once, and not on UI thread
            try {
                ins = openHttpConnection(urlIn);
                in = new BufferedReader(new InputStreamReader(ins));

                /*  //this approach only reads in a stream
                URL url = new URL(urlIn);
                in = new BufferedReader(
                        new InputStreamReader(
                                url.openStream()));
                */

                String inputLine;
                String curr_city = "";
                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.contains("row") ) {//find city
                        //System.out.println("FOUND ROW CITY: " + inputLine.toString() + " " + inputLine.toString().length());
                        String cityLine = inputLine.toString().substring(48, inputLine.toString().length()-5);
                        if (cityLine.charAt(cityLine.length()-1) != ' ') {
                            //System.out.println("Thecity: " + cityLine);
                            curr_city = cityLine;
                        }

                        inputLine = in.readLine(); //skip a line
                        inputLine = in.readLine(); //get old population data

                        Matcher m = tPattern.matcher(inputLine);
                        if (m.find()) {
                            s = m.group(1);
//                            if (curr_city.equals("Aquinnah")) {
//                                System.out.println("s, or old data: " + s);
//                            }
                        }
                        inputLine = in.readLine(); //skip a line
                        inputLine = in.readLine(); //get new population data

                        Matcher mm = tPattern.matcher(inputLine);
                        if (mm.find()) {
                            ss = mm.group(1);
                        }
                        boolean add = true;
                        for (City c : cityList) {
                            if (c.getName().equals(curr_city)) {
                                add = false;
                            }
                        }
                        if (add) {
                            cityList.add(new City(curr_city, s, ss));
                        }
                    }
                }
                //System.out.println("Loop done, list: ");
                //for (City city : cityList) {
                //    System.out.println(city.toString());
                //}
                //return (s + ";" + ss);  //a string with both values
                //return getString(R.string.unknown);  // never found the pattern
            } catch (IOException e) {
                Log.e("ScrapeTemperatures", "Unable to open url: " + url);
                //return getString(R.string.unknown);
            } catch (Exception e) {
                Log.e("ScrapeTemperatures", e.toString());
                //return getString(R.string.unknown);
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
            //displayProgress(message);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            String[] parts = result.split("\\;");
            String oldp = parts[0];
            //String newP = parts[1];

            TextView wait = (TextView) findViewById(R.id.waitText);
            wait.setText("Good to Go!");

            //displayAnswer(oldp,newP);
            //Make progress bar disappear
            //progressBar.setVisibility(View.INVISIBLE);

            ArrayAdapter<City> aa = new ArrayAdapter<City>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, cityList);
            spin.setAdapter(aa);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
