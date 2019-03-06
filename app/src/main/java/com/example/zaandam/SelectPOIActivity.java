package com.example.zaandam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geocoder.GeocoderCriteria;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Use the Mapbox Geocoding API to retrieve various information about a set of coordinates.
 */
public class SelectPOIActivity extends AppCompatActivity {

    public static LatLng destination = new LatLng();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeGeocodeSearch(destination);
    }

    private void makeGeocodeSearch(LatLng latlng) {
        try {
            // Build a Mapbox geocoding request
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(latlng.getLongitude(), latlng.getLatitude()))
                    .geocodingTypes(GeocoderCriteria.TYPE_POI)
                    .build();


            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                    List<CarmenFeature> results = response.body().features();

                    if (results.size() > 0) {
                        // Log the first results Point.
                        results.get(0).type();
                        // that returns the category of the place!!
                        Log.d("INFO", "onResponse: " + results.get(0).properties().get("category"));
                        Toast.makeText(SelectPOIActivity.this, "onResponse: "+ results.get(0).properties().get("category"), Toast.LENGTH_LONG).show();
                    } else {
                        // No result for your request were found.
                        Log.d("ERROR", "onResponse: No result found");
                    }
                }
                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    throwable.printStackTrace();
                }
            });

        } catch (ServicesException servicesException) {
            Log.d("ERROR", "Error geocoding: " + servicesException.toString());
            servicesException.printStackTrace();
        }
    }


    // Add the mapView lifecycle to the activity's lifecycle methods
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
