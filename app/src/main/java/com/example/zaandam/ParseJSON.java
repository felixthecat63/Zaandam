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
    ArrayList<Float> distances = new ArrayList<>();

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
            double haversineDistance = Haversine.computeDistance(lat1, lng1, lat2, lng2);
            float distance = (float) haversineDistance;

            if (distance <= range && results.size() <=20) {
                results.add(json_object);
                distances.add(distance);
            }

            // prepare results in order to use them in the MapActivity
            MapActivity.geoJson = prepareResultsForGeoJson();
        }

        //sort results
        sort();

        // give results to show results activity
        ShowResultsActivity.results = results;
        ShowResultsActivity.distances = distances;

        Log.d("INFO", "number of results found:" + results.size()+"");
        for (int i=0; i<results.size(); i++) {
            Log.d("INFO",  results.get(i).get("city")+" - "+distances.get(i)+" km");
        }
    }

    // Sort the results from the nearest to the farthest
    private void sort() {
        int iterations = results.size();
        ArrayList<JSONObject> jsonObjectsTemp = new ArrayList<>();
        ArrayList<Float> distancesTemp = new ArrayList<>();
        int index;
        Log.d("INFO",  "size before sorting : "+results.size());
        for (int i=0; i<iterations; i++) {
            index = minimum(distances);
            Log.d("INFO",  "adding");
            jsonObjectsTemp.add(results.get(index));
            distancesTemp.add(distances.get(index));
            results.remove(index);
            distances.remove(index);
        }
        Log.d("INFO",  "size after sorting : "+results.size());
        results = jsonObjectsTemp;
        distances = distancesTemp;
    }

    private int minimum(ArrayList<Float> distances) {
        int minimum = 0;
        for (int i=1; i<distances.size(); i++) {
            if (distances.get(i) < distances.get(minimum)) {
                minimum = i;
            }
        }
        return minimum;
    }

    private String prepareResultsForGeoJson() throws JSONException {
        String geoJson = "{\"type\": \"FeatureCollection\", \"features\": [";
        for (int i=0; i<results.size(); i++) {
            geoJson = geoJson + "{\"type\": \"Feature\", \"properties\": {\"name\":\"" + results.get(i).get("name").toString() + "\", \"gps\":\""+results.get(i).get("latitude").toString()+", "+ results.get(i).get("longitude").toString()+"\", \"category\":\""+ChoosePOICategory.category+"\", \"description\":\"" + results.get(i).get("description").toString() + "\"}, \"geometry\": {\"type\":\"Point\", \"coordinates\": ["+results.get(i).get("latitude").toString()+", "+results.get(i).get("longitude").toString() + "]}}";
            if (i != results.size()-1) {
                geoJson = geoJson + ",";
            }
        }
        geoJson = geoJson + "]}";
        return geoJson;
    }

}