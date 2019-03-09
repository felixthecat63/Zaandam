package com.example.zaandam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONException;

public class ChoosePOICategory extends AppCompatActivity {

    public static String category = "";
    public static int range = 0;
    public static LatLng destinationCoordinates = new LatLng();
    public static String destinationAddress = "";
    private RadioGroup radioGroupCategory;
    private RadioGroup radioGroupRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_poi_category);

        radioGroupCategory = (RadioGroup) findViewById(R.id.radioGroupCategory);
        radioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    category = giveFileName(rb.getText().toString());
                    Log.d("INFO", "category: " + category);
                }
                else {
                    Toast.makeText(ChoosePOICategory.this, "Select a category", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroupRange = (RadioGroup) findViewById(R.id.radioGroupRange);
        radioGroupRange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    range = giveRange(rb.getText().toString());
                    Log.d("INFO", "range: " + range);
                }
                else {
                    Toast.makeText(ChoosePOICategory.this, "Select a range", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onConfirmPOICriteria(View v) {
        Log.d("INFO", "range & category onConfirmPOICriteria: " + range+" " +category);
        if (!category.equals("") && range != 0) {
            ParseJSON p = new ParseJSON();
            String json = p.loadJSONFromAsset(this, category);
            try {
                p.readJSON(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(ChoosePOICategory.this, "Select a category and a range", Toast.LENGTH_SHORT).show();
        }
    }

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

    public String giveFileName(String choice) {
        switch (choice) {
            case "Airports":
                return "airport";
            case "ATMs":
                return "atm";
            case "Attractions":
                return "attraction";
            case "Bakeries":
                return "bakery";
            case "Campsites":
                return "campsite";
            case "Food stores":
                return "foodStores";
            case "Free WiFi":
                return "freeWiFi";
            case "Hotels":
                return "hotel";
            case "Lakes":
                return "lake";
            case "Mc Donalds":
                return "mcDonalds";
            case "Monuments":
                return "monument";
            case "Parking":
                return "parking";
            case "Petrol stations":
                return "petrolStation_leclerc";
            case "Picnic area":
                return "picnicArea";
            case "Railway stations":
                return "railwayStation";
            case "Religious buildings":
                return "religiousBuilding";
            case "Restaurants":
                return "restaurant";
            case "Seaside":
                return "seaside";
            case "Theatre":
                return "theatre";
            case "Tram stations":
                return "tramStation";
            default:
                return "restaurant";
        }
    }

    public int giveRange(String choice) {
        switch (choice) {
            case "5 kms":
                return 5;
            case "10 kms":
                return 10;
            case "20 kms":
                return 20;
            default:
                return 0;
        }
    }
}
