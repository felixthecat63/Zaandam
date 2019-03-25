package com.example.zaandam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mapbox.api.directions.v5.DirectionsCriteria;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
 

public class DirectionActivity extends AppCompatActivity  {


	private Button car;
    private Button bike;
    private Button walking;
   // private Button mplay;

    private String profil;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_direction);

           
			if (getIntent().hasExtra("latitude")&& getIntent().hasExtra("longitude"))
			{
            latitude=getIntent().getStringExtra("latitude");
            longitude=getIntent().getStringExtra("longitude");

			}
        car =(Button)findViewById(R.id.car);
        bike =(Button)findViewById(R.id.cycling);
        walking =(Button)findViewById(R.id.walking);
        //mplay=(Button)findViewById(R.id.mplay);

		/*mplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NavigationActivity = new Intent(DirectionActivity.this, NavigationActivity.class);
                startActivity(NavigationActivity);
            }
        });*/

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                profil= DirectionsCriteria.PROFILE_DRIVING;

                Intent map = new Intent(DirectionActivity.this, DirectionMapActivity.class);
                map.putExtra("profil",profil);
                map.putExtra("latitude",longitude);
                map.putExtra("longitude",latitude);

                startActivity(map);
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                profil=DirectionsCriteria.PROFILE_CYCLING;

                Intent map = new Intent(DirectionActivity.this, DirectionMapActivity.class);
                map.putExtra("profil",profil);
                map.putExtra("latitude",longitude);
                map.putExtra("longitude",latitude);

                startActivity(map);
            }
        });

        walking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                profil=DirectionsCriteria.PROFILE_WALKING;

                Intent map = new Intent(DirectionActivity.this, DirectionMapActivity.class);
                map.putExtra("profil",profil);
                map.putExtra("latitude",longitude);
                map.putExtra("longitude",latitude);


                startActivity(map);
            }
        });




}
}

