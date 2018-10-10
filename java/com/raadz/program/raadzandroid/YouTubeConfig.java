package com.raadz.program.raadzandroid;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.facebook.FacebookSdk.getApplicationContext;

public class YouTubeConfig {
    YouTubeConfig(){

    }

    private static final String API_KEY = "AIzaSyBa_YrX5REhZZdR0uOmqcejy4cSPa8s3NI";

    public static String getApiKey(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String API_KEY_TEMP = preferences.getString("youtube_api_key", "");
        return API_KEY_TEMP;
    }
}
