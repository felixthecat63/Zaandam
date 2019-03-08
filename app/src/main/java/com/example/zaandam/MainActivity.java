package com.example.zaandam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    /** Called when the user taps on the search button (select a destinationCoordinates) */
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
