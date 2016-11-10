/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package xavierpaul.meteox2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;

import android.view.View;
import android.widget.ListView;

public class CitiesPicker extends Activity {
    private SimpleAdapter m_citiesListAdapter;
    public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_picker);

        m_citiesListAdapter = new SimpleAdapter(new ArrayList<City>(), this);
        ListView lView = (ListView) findViewById(R.id.listview);

        lView.setAdapter(m_citiesListAdapter);

        // Exec async load task
        (new AsyncListViewLoader()).execute("http://www.prevision-meteo.ch/services/json/list-cities");
    }

    public void CityClicked(View view) {
        String v_url = (String) view.getTag();
        Intent intent = new Intent(this, WeatherByCity.class);

        Intent v_newTabbedActivity = new Intent(this, WeatherDetailsByCity.class);

        intent.putExtra(EXTRA_MESSAGE, v_url);
        startActivity(/*intent*/v_newTabbedActivity);
    }


    private class AsyncListViewLoader extends AsyncTask<String, Void, List<City>> {
        private final ProgressDialog dialog = new ProgressDialog(CitiesPicker.this);

        @Override
        protected void onPostExecute(List<City> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            m_citiesListAdapter.setItemList(result);
            m_citiesListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading cities list...");
            dialog.show();
        }

        @Override
        protected List<City> doInBackground(String... params) {
            List<City> result = new ArrayList<City>();

            try {
                JSONObject Jobject = GetJSONFromURL(params[0]);

                int v_size = Jobject.names().length();

                for (int i = 0; i < v_size; i++) {
                    JSONObject test = (JSONObject) Jobject.get(Integer.toString(i));
                    City v_newCity = convertContact(test);

                    if (v_newCity.get_country().equals("FRA"))
                        result.add(v_newCity);
                }

                //on trie par ordre alphabétique
                Collections.sort(result, new Comparator<City>() {
                    @Override
                    public int compare(City s1, City s2) {
                        return s1.toString().compareToIgnoreCase(s2.toString());
                    }
                });

                return result;
            } catch (Throwable t) {
                t.printStackTrace();
                ShowMessage("Error", t.getMessage());
            }
            return null;
        }

        private City convertContact(JSONObject obj) throws JSONException {
            //name":"Bagimont","npa":"5550","region":"","country":"BEL","url":"bagimont"}

            String name = obj.getString("name");
            String npa = obj.getString("npa");
            String region = obj.getString("region");
            String country = obj.getString("country");
            String url = obj.getString("url");

            return new City(name, npa, region, country, url, 1);
        }

    }

    public static JSONObject GetJSONFromURL(String p_url) {
        try {
            URL u = new URL(p_url);

            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");

            conn.connect();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            String JSONResp = "";
            while ((line = reader.readLine()) != null) {
                JSONResp = new String(line);
            }

            return new JSONObject(JSONResp);
        }
        catch (Throwable t) {
            t.printStackTrace();
            //ShowMessage("Error", t.getMessage());
        }
        return null;
    }

    private void ShowMessage(String p_title, String p_message) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(p_message);
        dlgAlert.setTitle(p_title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });

        dlgAlert.create().show();
    }

}
