package com.raadz.program.raadzandroid;

public class TwitterConfig {
    TwitterConfig(){

    }

    private static final String TWITTER_API_KEY = "CUeZCJZ1h0ITlTEzIsU5fr1Zj";
    private static final String TWITTER_SECRET_KEY = "R6tv6Z2jWCAjs2GlUcuR7xRuD7EBiwtyzP3sNeOrFdJjxGal1H\n";

    public static String getPubKey(){
        return TWITTER_API_KEY;
    }

    public static String getSecretKey(){
        return TWITTER_SECRET_KEY;
    }
}