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

        // I need to transform the string in an array because there are duplicate values in the JSON file
        JSONArray array = new JSONArray(json);

        for (int i=0; i<array.length(); i++) {
            // string_object is an element of the JSON file
            String string_object = array.get(i).toString();
            // I transform the object in a JSON object so I can explore its values by using the keys
            JSONObject json_object = new JSONObject(string_object);
            // Retrieve the GPS coordinates of the POI
            json_object.get("city");
            Log.d("INFO", "coming from JSON : "+json_object.get("city"));
        }
    }
}