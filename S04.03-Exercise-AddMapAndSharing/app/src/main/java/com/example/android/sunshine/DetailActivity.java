package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private String mForecast;
    private TextView mWeatherDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWeatherDisplay = (TextView) findViewById(R.id.tv_display_weather);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mWeatherDisplay.setText(mForecast);
            }
        }
    }

    // Done (3) Create a menu with an item with id of action_share

    // Done (4) Display the menu and implement the forecast sharing functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.detail, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            shareWeatherData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareWeatherData(){
        String chooserTitle = "Share your weather using:";
        Intent sendIntent = null;

        try {
            sendIntent = ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText("This is my location weather data:" + mForecast).getIntent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(sendIntent,chooserTitle));
        }
    }
}