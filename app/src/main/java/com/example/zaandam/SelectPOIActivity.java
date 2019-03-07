package com.example.zaandam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;

/**
 * Use the Mapbox Geocoding API to retrieve various information about a set of coordinates.
 */
public class SelectPOIActivity extends AppCompatActivity {

    public static LatLng destination = new LatLng();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseJSON p = new ParseJSON();
        String json = p.loadJSONFromAsset(this, "mcDonalds");
        try {
            p.readJSON(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /** Called when the user taps on the confirm location button (select a destination) */
    public void confirmLocation (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
