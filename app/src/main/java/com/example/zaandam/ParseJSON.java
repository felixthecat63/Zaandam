package com.example.zaandam;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ParseJSON {

    ArrayList<JSONObject> results = new ArrayList<>();
    ArrayList<Double> distances = new ArrayList<>();

    // Read the JSON file and transform it in a String
    public String loadJSONFromAsset(Context context, String category) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(category + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;
    }

    // Parse the string retrieved from loadJSONFromAssets
    public void readJSON(String json) throws JSONException {

        // Retrieve the coordinates of the destinationCoordinates selected previously by the user
        Double lat1 = ChoosePOICategory.destinationCoordinates.getLatitude();
        Double lng1 = ChoosePOICategory.destinationCoordinates.getLongitude();
        int range = ChoosePOICategory.range;

        // I need to transform the string in an array because there are duplicate values in the JSON file
        JSONArray array = new JSONArray(json);

        for (int i=0; i<array.length(); i++) {
            // string_object is an element of the JSON file
            String string_object = array.get(i).toString();
            // I transform the object in a JSON object so I can explore its values by using the keys
            JSONObject json_object = new JSONObject(string_object);

            // Retrieve the GPS coordinates of the POI
            Double lat2 = Double.parseDouble(json_object.get("longitude").toString());
            Double lng2 = Double.parseDouble(json_object.get("latitude").toString());

            // Compute distance between destinationCoordinates selected by the user and position of the current POI
            Double distance = Haversine.computeDistance(lat1, lng1, lat2, lng2);

            if (distance <= range) {
                results.add(json_object);
                distances.add(distance);
            }
        }

        //sort results
        sort();

        Log.d("INFO", "number of results found:" + results.size()+"");
        for (int i=0; i<results.size(); i++) {
            Log.d("INFO",  results.get(i).get("city")+" - "+distances.get(i)+" km");
        }
    }

    // Sort the results from the nearest to the farthest
    private void sort() {
        ArrayList<JSONObject> jsonObjectsTemp = new ArrayList<>();
        ArrayList<Double> distancesTemp = new ArrayList<>();
        int index;
        for (int i=0; i<results.size(); i++) {
            index = minimum(distances);
            jsonObjectsTemp.add(results.get(index));
            distancesTemp.add(distances.get(index));
            results.remove(index);
            distances.remove(index);
        }
        results = jsonObjectsTemp;
        distances = distancesTemp;
    }

    private int minimum(ArrayList<Double> distances) {
        int minimum = 0;
        for (int i=0; i<distances.size(); i++) {
            if (distances.get(i) < distances.get(minimum)) {
                minimum = i;
            }
        }
        return minimum;
    }

}