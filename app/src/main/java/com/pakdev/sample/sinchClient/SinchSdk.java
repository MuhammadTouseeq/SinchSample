package com.pakdev.sample.sinchClient;

import android.content.Context;

import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;

public class SinchSdk {


    private static final String APP_KEY = "2aab6316-1df9-4a41-95a9-863e1fb39dbe";
    private static final String APP_SECRET = "fkUKDvImP0OB8SzXk8Smgg==";
    private static final String ENVIRONMENT = "sandbox.sinch.com";
    public static String CALL_ID="call-id";
    public static String RECIPENT_ID="Touseeq";
    public static String USER_ID="Ahmed";


    private static SinchClient mSinchClient;

    public static SinchClient getInstance(Context context) {


        if(mSinchClient==null) {

            mSinchClient = Sinch.getSinchClientBuilder().context(context).userId(USER_ID)
                    .applicationKey(APP_KEY)
                    .applicationSecret(APP_SECRET)
                    .environmentHost(ENVIRONMENT).build();
            mSinchClient.setSupportCalling(true);
            mSinchClient.setSupportMessaging(true);
            mSinchClient.startListeningOnActiveConnection();
            mSinchClient.start();


        }

        return mSinchClient;
    }

    private SinchSdk() {
    }




}
