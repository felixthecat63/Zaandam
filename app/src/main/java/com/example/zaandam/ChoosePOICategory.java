package com.example.zaandam;

import android.content.Intent;
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
                Intent intent = new Intent(this, ShowResultsActivity.class);
                startActivity(intent);
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
        if (choice.equals(getString(R.string.airports))) {
            return "airport";
        }
        else if (choice.equals(getString(R.string.atms))) {
            return "atm";
        }
        else if (choice.equals(getString(R.string.attractions))) {
            return "attraction";
        }
        else if (choice.equals(getString(R.string.bakeries))) {
            return "bakery";
        }
        else if (choice.equals(getString(R.string.campsites))) {
            return "campsite";
        }
        else if (choice.equals(getString(R.string.food_stores))) {
            return "foodStores";
        }
        else if (choice.equals(getString(R.string.free_wifi))) {
            return "freeWiFi";
        }
        else if (choice.equals(getString(R.string.hotels))) {
            return "hotel";
        }
        else if (choice.equals(getString(R.string.lakes))) {
            return "lake";
        }
        else if (choice.equals(getString(R.string.mc_donalds))) {
            return "mcDonalds";
        }
        else if (choice.equals(getString(R.string.monuments))) {
            return "monument";
        }
        else if (choice.equals(getString(R.string.parking))) {
            return "parking";
        }
        else if (choice.equals(getString(R.string.petrol_station))) {
            return "petrolStation_leclerc";
        }
        else if (choice.equals(getString(R.string.picnic))) {
            return "picnicArea";
        }
        else if (choice.equals(getString(R.string.religious_buildings))) {
            return "religiousBuilding";
        }
        else if (choice.equals(getString(R.string.restaurants))) {
            return "restaurant";
        }
        else if (choice.equals(getString(R.string.seaside))) {
            return "seaside";
        }
        else if (choice.equals(getString(R.string.service_area))) {
            return "serviceArea";
        }
        else if (choice.equals(getString(R.string.theatre))) {
            return "theatre";
        }
        else if (choice.equals(getString(R.string.tram_stations))) {
            return "tramStation";
        }
        else if (choice.equals(getString(R.string.railway_station))) {
            return "railwayStation";
        }
        return "hotel";
    }

    public int giveRange(String choice) {
        switch (choice) {
            case "5":
                return 5;
            case "10":
                return 10;
            case "20":
                return 20;
            case "50":
                return 50;
            default:
                return 0;
        }
    }
}
