package com.example.android.sunshine.sync;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

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
// Done (2) Make sure you've imported the jobdispatcher.JobService, not job.JobService
// Done (3) Add a class called SunshineFirebaseJobService that extends jobdispatcher.JobService
public class SunshineFirebaseJobService extends JobService {
    //  Done (4) Declare an ASyncTask field called mFetchWeatherTask
    private static AsyncTask<Void, Void, Void> mFetchWeatherTask;

    //  Done (5) Override onStartJob and within it, spawn off a separate ASyncTask to sync weather data
    //  Done (6) Once the weather data is sync'd, call jobFinished with the appropriate arguments
    @Override
    public boolean onStartJob(@NonNull final JobParameters job) {
        mFetchWeatherTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                SunshineSyncTask.syncWeather(getApplicationContext());
                Log.d("TAG", "job started syncWeather is called");
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jobFinished(job, false);
                Log.d("TAG", "job finished");
            }
        };
        mFetchWeatherTask.execute();
        return true;
    }

    //  Done (7) Override onStopJob, cancel the ASyncTask if it's not null and return true
    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        if (mFetchWeatherTask != null) {
            mFetchWeatherTask.cancel(true);
        }
        Log.d("TAG", "job stopped, featchWeatherTask canceled ");
        return true;
    }
}
