package com.pakdev.sample.activity.messaging;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pakdev.sample.Models.UserChat;
import com.pakdev.sample.R;
import com.pakdev.sample.Repository.DbRepository;
import com.pakdev.sample.sinchClient.SinchSdk;


import com.pakdev.sample.video.CallScreenActivity;
import com.pakdev.sample.video.IncomingCallScreenActivity;
import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;
import com.sinch.android.rtc.video.VideoCallListener;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class MessagingActivity extends AppCompatActivity implements MessageClientListener {

    private static final String TAG = MessagingActivity.class.getSimpleName();

    private MessageAdapter mMessageAdapter;
    private EditText mTxtRecipient;
    private EditText mTxtTextBody;
    private ImageView mBtnSend;
    private Toolbar toolbar;
    private ImageView btnCall;
    private SinchClient instance;
    private ImageView btnVideo;
    DbRepository repo;
    UserChat uc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging);
        repo = new DbRepository(this);
        uc = new UserChat();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mTxtTextBody = (EditText) findViewById(R.id.txtTextBody);
        btnCall = (ImageView) findViewById(R.id.btnCall);
        btnVideo = (ImageView) findViewById(R.id.btnVideo);


        instance = SinchSdk.getInstance(getApplicationContext());

        instance.getCallClient().addCallClientListener(new SinchCallClientListener());

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallUser();

            }


        });

        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();

            }
        });

        mMessageAdapter = new MessageAdapter(this, repo.getAllChat());
        ListView messagesList = (ListView) findViewById(R.id.lstMessages);
        messagesList.setAdapter(mMessageAdapter);

        SinchSdk.getInstance(getApplicationContext()).getMessageClient().addMessageClientListener(this);

        mBtnSend = (ImageView) findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();

            }
        });
    }


    private void sendMessage() {
//        String recipient = mTxtRecipient.getText().toString();
        String textBody = mTxtTextBody.getText().toString();
//        if (recipient.isEmpty()) {
//            Toast.makeText(this, "No recipient added", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (textBody.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No text message", Toast.LENGTH_SHORT).show();
            return;
        }
        WritableMessage message = new WritableMessage(SinchSdk.RECIPENT_ID, textBody);
        SinchSdk.getInstance(getApplicationContext()).getMessageClient().send(message);

        mTxtTextBody.setText("");
    }

    private void setButtonEnabled(boolean enabled) {
        mBtnSend.setEnabled(enabled);
    }

    @Override
    public void onIncomingMessage(MessageClient client, Message message) {

        setMessages(message, String.valueOf(MessageAdapter.DIRECTION_INCOMING));
        //mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_INCOMING);
    }

    @Override
    public void onMessageSent(MessageClient client, Message message, String recipientId) {
        setMessages(message, String.valueOf(MessageAdapter.DIRECTION_OUTGOING));
        //mMessageAdapter.addMessage(message, MessageAdapter.DIRECTION_OUTGOING);
    }

    @Override
    public void onShouldSendPushData(MessageClient client, Message message, List<PushPair> pushPairs) {
        // Left blank intentionally
    }

    @Override
    public void onMessageFailed(MessageClient client, Message message,
                                MessageFailureInfo failureInfo) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sending failed: ")
                .append(failureInfo.getSinchError().getMessage());

        Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        Log.d(TAG, sb.toString());
    }

    @Override
    public void onMessageDelivered(MessageClient client, MessageDeliveryInfo deliveryInfo) {
        Log.d(TAG, "onDelivered");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "click..!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        MenuItem item = menu.findItem(R.id.create);
//        item.setVisible(isAdmin ? true : false);

        return true;

    }


    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            Log.d(TAG, "Incoming call");


            Intent intent = null;

            if (call.getDetails().isVideoOffered()) {
                intent = new Intent(MessagingActivity.this, IncomingCallScreenActivity.class);

            } else {
                intent = new Intent(MessagingActivity.this, com.pakdev.sample.activity.calling.CallScreenActivity.class);
            }
            intent.putExtra(SinchSdk.CALL_ID, call.getCallId());
            startActivity(intent);

        }
    }

    private void requestCameraPermission() {
        Dexter.withActivity(MessagingActivity.this)
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            videoCallUser();

                        }


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        token.continuePermissionRequest();
                    }
                }).onSameThread()
                .check();
    }

    private void CallUser() {
        Call call = SinchSdk.getInstance(getApplicationContext()).getCallClient().callUserVideo(SinchSdk.RECIPENT_ID);
        String callId = call.getCallId();
        Intent callScreen = new Intent(MessagingActivity.this, CallScreenActivity.class);
        callScreen.putExtra(SinchSdk.CALL_ID, callId);
        startActivity(callScreen);
    }

    private void videoCallUser() {
        Call call = SinchSdk.getInstance(getApplicationContext()).getCallClient().callUser(SinchSdk.RECIPENT_ID);
        String callId = call.getCallId();
        Intent callScreen = new Intent(MessagingActivity.this, CallScreenActivity.class);
        callScreen.putExtra(SinchSdk.CALL_ID, callId);
        startActivity(callScreen);
    }

    public void insertChatMessages(UserChat uc) {

        repo.insertItems(uc);
    }

    public void setMessages(Message message, String type) {
        uc.setMessageId(message.getMessageId());
        uc.setMessage(message.getTextBody());
        uc.setRecipientId(SinchSdk.RECIPENT_ID);
        uc.setSenderId(SinchSdk.USER_ID);
        uc.setMessageId(message.getMessageId());
        uc.setType(type);
        uc.setTimeStamp(String.valueOf(message.getTimestamp()));
        mMessageAdapter.addMessage(uc);
        insertChatMessages(uc);
    }
}

