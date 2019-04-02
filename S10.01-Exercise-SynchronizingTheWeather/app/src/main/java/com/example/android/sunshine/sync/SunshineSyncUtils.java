package com.example.android.sunshine.sync;

import android.content.Context;
import android.content.Intent;

// Done (9) Create a class called SunshineSyncUtils
public class  SunshineSyncUtils {
    //  Done (10) Create a public static void method called startImmediateSync
    public static void startImmediateSync (Context context) {
        //  Done(11) Within that method, start the SunshineSyncIntentService
        Intent sunshineSyncIntentService = new Intent (context, SunshineSyncIntentService.class);
        context.startService(sunshineSyncIntentService);
    }

}
