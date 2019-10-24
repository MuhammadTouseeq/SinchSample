package com.pakdev.sample.sinchClient;

import android.content.Context;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.messaging.WritableMessage;

public class SinchHelper {


    private SinchClient mSinchClient;

    public SinchHelper build(Context context)
    {
        mSinchClient=SinchSdk.getInstance(context);
        return this;
    }

    public SinchHelper callToClient(String callerId, CallListener callListener)
    {
        if(mSinchClient!=null)
        {
            if (isStarted()) {
                mSinchClient.getCallClient().callUser(callerId).addCallListener(callListener);
            }
        }

        return this;
    }

    public SinchHelper stopClient()
    {
        if (mSinchClient != null && mSinchClient.isStarted()) {
            mSinchClient.terminateGracefully();
        }
        return this;
    }

    public boolean isStarted() {
        return (mSinchClient != null && mSinchClient.isStarted());
    }

    public SinchHelper sendMessage(String recipientUserId, String textBody) {
        if (isStarted()) {
            WritableMessage message = new WritableMessage(recipientUserId, textBody);
            mSinchClient.getMessageClient().send(message);
        }

        return this;
    }


    public Call getCall(String callId) {
        return mSinchClient != null ? mSinchClient.getCallClient().getCall(callId) : null;
    }


}
