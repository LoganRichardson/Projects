package com.raadz.program.raadzandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Twitter_config {
    Twitter_config(){

    }

    private static final String API_KEY = "CUeZCJZ1h0ITlTEzIsU5fr1Zj";

    private static final String API_SECRET_KEY = "R6tv6Z2jWCAjs2GlUcuR7xRuD7EBiwtyzP3sNeOrFdJjxGal1H";

    public static String getApiKey(){
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String API_KEY_TEMP = preferences.getString("youtube_api_key", "");
        return API_KEY;
    }

    public static String getSecretKey(){
        return API_SECRET_KEY;
    }
}
