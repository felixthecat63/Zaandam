package com.example.zaandam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeocodingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Button chooseCityButton;
    private EditText latEditText;
    private EditText longEditText;
    private TextView destinationAddressTextView;
    public static Point destinationCoordinates = null;
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    public static String destinationAddress = null;

    Double latitude = null;
    Double longitude = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_geocoding);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                initTextViews();
                initButtons();
            }
        });
    }

    private void initTextViews() {
        latEditText = findViewById(R.id.geocode_latitude_editText);
        longEditText = findViewById(R.id.geocode_longitude_editText);
        destinationAddressTextView = findViewById(R.id.destination_address);
    }

    private void initButtons() {
        Button mapCenterButton = findViewById(R.id.map_center_button);
        Button startGeocodeButton = findViewById(R.id.start_geocode_button);
        chooseCityButton = findViewById(R.id.choose_city_spinner_button);
        startGeocodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // Make sure the EditTexts aren't empty
                if (TextUtils.isEmpty(latEditText.getText().toString())) {
                    latEditText.setError("fill in a valid");
                } else if (TextUtils.isEmpty(longEditText.getText().toString())) {
                    longEditText.setError("fill in a valid lat");
                } else {
                    if (latCoordinateIsValid(Double.valueOf(latEditText.getText().toString()))
                            && longCoordinateIsValid(Double.valueOf(longEditText.getText().toString()))) {
                    // Make a geocoding search with the values inputted into the EditTexts
                        makeGeocodeSearch(new LatLng(Double.valueOf(latEditText.getText().toString()),
                                Double.valueOf(longEditText.getText().toString())));
                    } else {
                        Toast.makeText(GeocodingActivity.this, "make valid lat", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        chooseCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                        .accessToken(getString(R.string.mapbox_access_token))
                        .query(destinationAddressTextView.getText().toString())
                        .build();

                mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
                    @Override
                    public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                        List<CarmenFeature> results = response.body().features();
                        LatLng cityLatLng = new LatLng();
                        if (results.size() > 0) {
                            // Log the first results Point.
                            Point firstResultPoint = results.get(0).center();
                            destinationCoordinates = firstResultPoint;
                            cityLatLng = new LatLng(firstResultPoint.latitude(), firstResultPoint.longitude());
                            setCoordinateEditTexts(cityLatLng);
                            animateCameraToNewPosition(cityLatLng);
                            makeGeocodeSearch(cityLatLng);
                            Log.d("INFO", "onResponse: " + firstResultPoint.latitude()+", "+firstResultPoint.longitude());
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
            }
        });
        mapCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 // Get the map's target
                LatLng target = mapboxMap.getCameraPosition().target;

                // Fill the coordinate EditTexts with the target's coordinates
                setCoordinateEditTexts(target);

                // Make a geocoding search with the target's coordinates
                makeGeocodeSearch(target);
            }
        });
    }

    public void selectDestination (View view) {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(getString(R.string.mapbox_access_token))
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(10)
                        .build(PlaceOptions.MODE_CARDS))
                .build(this);
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
    }

    private boolean latCoordinateIsValid(double value) {
        return value >= -90 && value <= 90;
    }

    private boolean longCoordinateIsValid(double value) {
        return value >= -180 && value <= 180;
    }

    private void setCoordinateEditTexts(LatLng latLng) {
        latEditText.setText(String.valueOf(latLng.getLatitude()));
        longEditText.setText(String.valueOf(latLng.getLongitude()));
        latitude = latLng.getLatitude();
        longitude = latLng.getLongitude();
    }

    private void makeGeocodeSearch(final LatLng latLng) {
        try {
            // Build a Mapbox geocoding request
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken(getString(R.string.mapbox_access_token))
                    .query(Point.fromLngLat(latLng.getLongitude(), latLng.getLatitude()))
                    .geocodingTypes(GeocodingCriteria.TYPE_PLACE)
                    .mode(GeocodingCriteria.MODE_PLACES)
                    .build();
            client.enqueueCall(new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call,
                                       Response<GeocodingResponse> response) {
                    List<CarmenFeature> results = response.body().features();
                    if (results.size() > 0) {
                        // Get the first Feature from the successful geocoding response
                        CarmenFeature feature = results.get(0);
                        Log.d("INFO", feature.placeName().toString());
                        destinationAddress = feature.placeName();
                        //geocodeResultTextView.setText(feature.placeName().toString());
                        animateCameraToNewPosition(latLng);
                        destinationAddressTextView.setText(destinationAddress);
                        ChoosePOICategory.destinationAddress = feature.placeName().toString();
                        Toast.makeText(GeocodingActivity.this, feature.placeName().toString(), Toast.LENGTH_LONG).show();

                        latitude = latLng.getLatitude();
                        longitude = latLng.getLongitude();
                    } else {
                        Toast.makeText(GeocodingActivity.this, "no results", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    throwable.printStackTrace();
                    Log.d("ERROR", "Geocoding Failure: " + throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            Log.d("ERROR", "Error geocoding: " + servicesException.toString());
            servicesException.printStackTrace();
        }
    }

    private void animateCameraToNewPosition(LatLng latLng) {
        mapboxMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(13)
                        .build()), 1500);
    }

    /** this method is used by the autocomplete function */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            CarmenFeature feature = PlaceAutocomplete.getPlace(data);
            destinationAddress = feature.text();
            destinationAddressTextView.setText(destinationAddress);
            ChoosePOICategory.destinationAddress = feature.text();
            Toast.makeText(this, feature.text(), Toast.LENGTH_LONG).show();
        }
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /** Called when the user taps on the confirm location button (select a destinationCoordinates) */
    public void confirmLocation (View view) {
        Intent intent = new Intent(this, ChoosePOICategory.class);

        ChoosePOICategory.destinationCoordinates.setLatitude(latitude);
        ChoosePOICategory.destinationCoordinates.setLongitude(longitude);

        startActivity(intent);
    }
}