package com.example.zaandam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Button getCurrentLocation=findViewById(R.id.getMyLocation);
        getCurrentLocation.setOnClickListener(new OnClickListener(){
            public void onClick(View view){

            }
        });
        */
    }

    /** Called when the user taps on the search button (select a destination) */
    public void selectDestination (View view) {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken("pk.eyJ1IjoiZmVsaXh0aGVjYXQ2MyIsImEiOiJjanJ0MW5sYWoyZ2VtNDNwbHp5aXN2ZXh2In0.LQr-tOgZu1TMv36-A7X3oQ")
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(10)
                        .build(PlaceOptions.MODE_CARDS))
                .build(this);
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show();
        }
    }

    /** Called when the user taps the getMyLocation button */
    public void getCurrentLocation(View view) {
        Intent intent = new Intent(this, DisplayMapActivity.class);
        startActivity(intent);
    }
}
