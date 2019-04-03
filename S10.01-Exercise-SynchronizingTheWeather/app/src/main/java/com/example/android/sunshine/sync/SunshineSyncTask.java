package com.example.android.sunshine.sync;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import org.json.JSONException;

import java.io.IOException;

// Done (1) Create a class called SunshineSyncTask
 public class SunshineSyncTask {
     static String response;
     static ContentValues[] contentValues;
    // Done (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
     synchronized public static void syncWeather (Context context){
         // Done (3) Within syncWeather, fetch new weather data
         try {
           response = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.getUrl(context));
         } catch (IOException e) {
             e.printStackTrace();
         }
         // Done (4) If we have valid results, delete the old data and insert the new
        if( response != null){
            try {
               contentValues = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context,response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        if (contentValues != null&& contentValues.length != 0){
            context.getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
            context.getContentResolver().bulkInsert(
                    WeatherContract.WeatherEntry.CONTENT_URI,
                    contentValues);
        }
        }
     }
}

