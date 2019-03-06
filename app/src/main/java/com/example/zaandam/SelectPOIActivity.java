package com.example.zaandam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class SelectPOIActivity extends AppCompatActivity {

    public static LatLng destination = new LatLng();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_poi);

        Toast.makeText(this, destination.getLatitude()+", "+destination.getLongitude(), Toast.LENGTH_LONG).show();
    }
}
