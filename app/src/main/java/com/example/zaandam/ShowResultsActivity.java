package com.example.zaandam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShowResultsActivity extends AppCompatActivity {

    public static ArrayList<JSONObject> results = new ArrayList<>();
    public static ArrayList<Float> distances = new ArrayList<>();

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_results);

        mListView = (ListView) findViewById(R.id.listView);
        List<Row> results = null;
        try {
            results = generateResults();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ResultAdapter adapter = new ResultAdapter(ShowResultsActivity.this, results);
        mListView.setAdapter(adapter);
    }

    private List<Row> generateResults() throws JSONException {
        List<Row> rowResults = new ArrayList<Row>();

        for (int i=0; i<results.size(); i++) {
            int y = (int) Math.round(distances.get(i));
            rowResults.add(new Row(y, results.get(i).get("name").toString(), results.get(i).get("city").toString() + ", " + results.get(i).get("description").toString(), results.get(i).get("latitude").toString(), results.get(i).get("longitude").toString()));
        }
        return rowResults;
    }
}
