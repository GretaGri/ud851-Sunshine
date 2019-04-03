/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine.sync;

import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.sunshine.data.WeatherContract;


public class SunshineSyncUtils {

//  Done(1) Declare a private static boolean field called sInitialized
    private static boolean sInitialized;

    //  Done (2) Create a synchronized public static void method called initialize
    synchronized public static void initialize(final Context context){
        //  Done (3) Only execute this method body if sInitialized is false
        if (!sInitialized) {
            //  Done(4) If the method body is executed, set sInitialized to true
            sInitialized = true;

            //  Done (5) Check to see if our weather ContentProvider is empty
                 new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    /* URI for every row of weather data in our weather table*/
                    Uri forecastQueryUri = WeatherContract.WeatherEntry.CONTENT_URI;

                    /*
                     * Since this query is going to be used only as a check to see if we have any
                     * data (rather than to display data), we just need to PROJECT the ID of each
                     * row. In our queries where we display data, we need to PROJECT more columns
                     * to determine what weather details need to be displayed.
                     */
                    String[] projectionColumns = {WeatherContract.WeatherEntry._ID};
                    String selectionStatement = WeatherContract.WeatherEntry
                            .getSqlSelectForTodayOnwards();

                    /* Here, we perform the query to check to see if we have any weather data */
                    Cursor cursor = context.getContentResolver().query(
                            forecastQueryUri,
                            projectionColumns,
                            selectionStatement,
                            null,
                            null);
                    /*
                     * A Cursor object can be null for various different reasons. A few are
                     * listed below.
                     *
                     *   1) Invalid URI
                     *   2) A certain ContentProvider's query method returns null
                     *   3) A RemoteException was thrown.
                     *
                     * Bottom line, it is generally a good idea to check if a Cursor returned
                     * from a ContentResolver is null.
                     *
                     * If the Cursor was null OR if it was empty, we need to sync immediately to
                     * be able to display data to the user.
                     */
                    //  Done(6) If it is empty or we have a null Cursor, sync the weather now!
                    if (null == cursor || cursor.getCount() == 0) {
                        startImmediateSync(context);
                    }

                    /* Make sure to close the Cursor to avoid memory leaks! */
                    cursor.close();
                    return null;
                }
            }.execute();
        }
    }

    /**
     * Helper method to perform a sync immediately using an IntentService for asynchronous
     * execution.
     *
     * @param context The Context used to start the IntentService for the sync.
     */
    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, SunshineSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}