package com.example.zaandam;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {

    private String en ="en", fr="fr", it="it";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
    }

    public void setLocaleEn(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(SelectLanguageActivity.this, en);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setLocaleFr(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(SelectLanguageActivity.this, fr);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void setLocaleIt(View view) {
        //Change Application level locale
        LocaleHelper.setLocale(SelectLanguageActivity.this, it);

        //It is required to recreate the activity to reflect the change in UI.
        recreate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
