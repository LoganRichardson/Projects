package com.raadz.program.raadzandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.facebook.FacebookSdk.getApplicationContext;

public class GoogleConfig {
    GoogleConfig(){
    }

    public static String getApiKey(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String API_KEY_TEMP = preferences.getString("google_oauth2", "");
        return API_KEY_TEMP;
    }
}
