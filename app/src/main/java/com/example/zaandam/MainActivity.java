package com.example.zaandam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    public static LatLng destination = new LatLng();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Toast.makeText(this, destination.getLatitude()+", "+destination.getLongitude(), Toast.LENGTH_LONG).show();

        // define here the behaviour of the button
        /*
        Button getCurrentLocation=findViewById(R.id.getMyLocation);
        getCurrentLocation.setOnClickListener(new OnClickListener(){
            public void onClick(View view){
                // definir ici le comportement du bouton
            }
        });
        */
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /** Called when the user taps on the search button (select a destination) */
    public void selectDestination (View view) {
        Intent intent = new Intent(this, GeocodingActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the getMyLocation button */
    public void getCurrentLocation(View view) {
        Intent intent = new Intent(this, DisplayMapActivity.class);
        startActivity(intent);
    }
}
