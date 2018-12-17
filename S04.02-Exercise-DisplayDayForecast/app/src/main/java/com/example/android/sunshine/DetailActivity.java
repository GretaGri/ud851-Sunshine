package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private TextView weatherDetailText;
    private String weatherString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Done (2) Display the weather forecast that was passed from MainActivity
        weatherDetailText = (TextView) findViewById(R.id.tv_weather_details);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                weatherString = intent.getStringExtra(Intent.EXTRA_TEXT);
                weatherDetailText.setText(weatherString);
            }
        }
    }
}