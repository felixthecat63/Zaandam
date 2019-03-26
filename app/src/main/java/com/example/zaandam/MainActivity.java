package com.example.zaandam;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private String en ="en", fr="fr", it="it";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            boolean internet = isInternet();
            if (internet){

            }else{
                   // for(int i=0; i<2; i++) {
                        Toast toast = new Toast(getApplicationContext());
                        ImageView view = new ImageView(getApplicationContext());
                        view.setImageResource(R.drawable.offline);
                        toast.setView(view);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                CharSequence text = getString(R.string.toast_internet);
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                    //}
            }
        } catch (IOException e) {
            e.printStackTrace();
            CharSequence text = getString(R.string.toast_internet);
            Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
        }

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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.choose:
                select();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void select(){
        Intent intent = new Intent(this, SelectLanguageActivity.class);
        startActivity(intent);
    }


    /** Called when the user taps on the search button (select a destinationCoordinates) */
    public void selectDestination (View view) throws IOException {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, GeocodingActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "No internet connection detected: your location can't be retrieved but you can use the offline mode", Toast.LENGTH_LONG).show();
        }
    }

    /** Called when the user taps the getMyLocation button */
    public void getCurrentLocation(View view) throws IOException {
        if (isNetworkAvailable()) {
            Intent intent = new Intent(this, DisplayMapActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(MainActivity.this, "No internet connection detected: your location can't be retrieved but you can use the offline mode", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() throws IOException {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            String cmdPing = "ping www.google.com";
            Runtime r = Runtime.getRuntime();
            Process p = r.exec(cmdPing);
            BufferedReader in = new BufferedReader(	new InputStreamReader(p.getInputStream()));
            String inputLinhe = in.readLine();
        return  activeNetworkInfo != null && activeNetworkInfo.isConnected()&& inputLinhe!=null;
    }

    public void setLocaleIt(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(MainActivity.this, en);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void setLocaleFr(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(MainActivity.this, fr);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setLocaleEn(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(MainActivity.this, it);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean isInternet() throws IOException {
        String cmdPing = "ping www.google.com";
        Runtime r = Runtime.getRuntime();
        Process p = r.exec(cmdPing);
        BufferedReader in = new BufferedReader(	new InputStreamReader(p.getInputStream()));
        String inputLinhe = in.readLine();
        boolean a = inputLinhe==null ? false : true;
        return   a;
    }
}
