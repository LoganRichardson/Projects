package com.raadz.program.raadzandroid;

public class StripeConfig {
    StripeConfig(){

    }

    private static final String STRIPE_PUB_KEY = "pk_test_ababab";
    private static final String STRIPE_SECRET_KEY = "sk_test_cdcdcd";

    public static String getPubKey(){
        return STRIPE_PUB_KEY;
    }

    public static String getSecretKey(){
        return STRIPE_SECRET_KEY;
    }
}