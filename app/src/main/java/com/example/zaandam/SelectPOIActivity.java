package com.example.zaandam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Use the Mapbox Geocoding API to retrieve various information about a set of coordinates.
 */
public class SelectPOIActivity extends AppCompatActivity {

    public static LatLng destination = new LatLng();
    // poi contains the list of the POIs retrieved next to the destination
    public List<CarmenFeature> poi = new ArrayList<CarmenFeature>();
    // poiCategories contains the categories of the POIs found next to the destination
    public ArrayList<String> poiCategories = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrievePOI2(destination);
        printCategories();
        Log.d("INFO", "hello, it's me");
        ParseJSON p = new ParseJSON();
        String json = p.loadJSONFromAsset(this, "hotel");
        try {
            p.readJSON(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("INFO", "json should be parsed");
    }

    private void retrievePOI2(LatLng latlng) {
        try {
            // Build a Mapbox geocoding request
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(latlng.getLongitude(), latlng.getLatitude()))
                    .geocodingTypes("poi")
                    .proximity(Point.fromLngLat(latlng.getLongitude(), latlng.getLatitude()))
                    .build();


            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                    List<CarmenFeature> results = response.body().features();
                    Log.d("INFO", "length of carmenFeature : "+results.size());
                    if (results.size() > 0) {
                        setPOI(results);
                        printCategories();
                        // Log the first results Point.
                        results.get(0).type();
                        // that returns the category of the place!!
                        Log.d("INFO", "onResponse: " + results.get(0).properties().get("category"));
                        Toast.makeText(SelectPOIActivity.this, "onResponse: "+ results.get(0).properties().get("category"), Toast.LENGTH_LONG).show();
                    } else {
                        // No result for your request were found.
                        Log.d("ERROR", "onResponse: No result found");
                        Toast.makeText(SelectPOIActivity.this, "No POI were found next to this place!", Toast.LENGTH_LONG).show();
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

    // get all the POI categories retrieved during retrievePOI() and fill the list poiCategories
    public void setPOI(List<CarmenFeature> poi) {
        for (int i=0; i<poi.size(); i++) {
            poiCategories.add(poi.get(i).properties().get("category").toString());
        }
    }

    public void printCategories() {
        for (int i=0; i<poiCategories.size(); i++) {
            Log.d("INFO", poiCategories.get(i));
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
