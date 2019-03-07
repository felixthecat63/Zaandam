package com.example.zaandam;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;

public class ParseJSON {

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

        // Retrieve the coordinates of the destination selected previously by the user
        Double lat1 = SelectPOIActivity.destination.getLatitude();
        Double lng1 = SelectPOIActivity.destination.getLongitude();

        Log.d("INFO", " lat1 :"+Double.toString(lat1));
        Log.d("INFO", " lng1 :"+Double.toString(lng1));

        // I need to transform the string in an array because there are duplicate values in the JSON file
        JSONArray array = new JSONArray(json);

        for (int i=0; i<3; i++) {
            // string_object is an element of the JSON file
            String string_object = array.get(i).toString();
            // I transform the object in a JSON object so I can explore its values by using the keys
            JSONObject json_object = new JSONObject(string_object);
            // Retrieve the GPS coordinates of the POI
            Double lat2 = Double.parseDouble(json_object.get("longitude").toString());
            Double lng2 = Double.parseDouble(json_object.get("latitude").toString());

            Log.d("INFO", " lat2 :"+Double.toString(lat2));
            Log.d("INFO", " lng2 :"+Double.toString(lng2));

            // Compute distance between destination selected by the user and position of the current POI
            Double distance = Haversine.computeDistance(lat1, lng1, lat2, lng2);

            Log.d("INFO", " distance :"+Double.toString(distance));
        }
    }
}