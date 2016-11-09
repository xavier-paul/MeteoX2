package xavierpaul.meteox2;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class WeatherByCity extends Activity {

    private CityDetailsAdapter m_cityDetailsAdp;

    public class AsyncCityDetailsLoader extends AsyncTask<String, Void, List<CityDetails>> {
        private final ProgressDialog dialog = new ProgressDialog(WeatherByCity.this);

        @Override
        protected void onPostExecute(List<CityDetails> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            m_cityDetailsAdp.setItemList(result);
            m_cityDetailsAdp.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Downloading city details...");
            dialog.show();
        }

        @Override
        protected List<CityDetails> doInBackground(String... params) {
            try {
                JSONObject Jobject = CitiesPicker.GetJSONFromURL(params[0]);
                return GetCityDetails(Jobject);
            } catch (Throwable t) {
                t.printStackTrace();
                //ShowMessage("Error", t.getMessage());
            }
            return null;
        }
    }

    private List<CityDetails> GetCityDetails(JSONObject Jobject) throws JSONException {

        List<CityDetails> v_result =  new ArrayList<CityDetails>();
        try {
            JSONArray v_allSections = Jobject.names();
            int v_size = v_allSections.length();
            for (int v_section = 0; v_section < v_size; v_section++) {
                String v_subSection = (String) v_allSections.get(v_section);

                JSONObject v_subSectionValues = Jobject.getJSONObject(v_subSection);
                int v_values = v_subSectionValues.names().length();
                for (int v_value = 0; v_value < v_values; v_value++) {
                    String v_key = (String) v_subSectionValues.names().get(v_value);
                    v_result.add(new CityDetails(v_key, v_subSectionValues.getString(v_key)));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return v_result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_by_city);

        Intent intent = getIntent();
        String v_url = intent.getStringExtra(CitiesPicker.EXTRA_MESSAGE);

        m_cityDetailsAdp = new CityDetailsAdapter(new ArrayList<CityDetails>(), this);
        ListView v_cityDetailsList = (ListView) findViewById(R.id.listview_city_details);
        v_cityDetailsList.setAdapter(m_cityDetailsAdp);

        // Exec async load task
        (new AsyncCityDetailsLoader()).execute("http://www.prevision-meteo.ch/services/json/" + v_url);
    }
}
