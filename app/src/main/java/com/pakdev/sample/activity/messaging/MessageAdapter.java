package com.pakdev.sample.activity.messaging;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pakdev.sample.Models.UserChat;
import com.pakdev.sample.R;
import com.pakdev.sample.Repository.DateConverter;
import com.sinch.android.rtc.messaging.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends BaseAdapter {

    public static final int DIRECTION_INCOMING = 0;

    public static final int DIRECTION_OUTGOING = 1;

    //private List<Pair<Message, Integer>> mMessages;
    private List<UserChat> mMessagesnew = new ArrayList<>();


    private LayoutInflater mInflater;

    public MessageAdapter(Activity activity, List<UserChat> uc) {
        this.mMessagesnew = uc;
        mInflater = activity.getLayoutInflater();
        //  mMessages = new ArrayList<Pair<Message, Integer>>();
        //   mFormatter = new SimpleDateFormat("HH:mm");
    }

    public void addMessage(UserChat chat_message) {
        //    mMessages.add(new Pair(message, direction));
        mMessagesnew.add(chat_message);
        // notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mMessagesnew != null) {
            return mMessagesnew.size();

        }
        return 0;

    }

    @Override
    public Object getItem(int i) {
        return mMessagesnew.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        int direction = mMessagesnew.get(i).getType();
        Log.d("##", "this is : " + direction);
        int res = 0;
        if (direction == DIRECTION_INCOMING) {
            res = R.layout.item_chat_left;

        } else if (direction == DIRECTION_OUTGOING) {
            res = R.layout.item_chat_right;

        }
        if (convertView == null) {

            convertView = mInflater.inflate(res, viewGroup, false);
        }


        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);

//        txtSender.setText(name);
        txtMessage.setText(mMessagesnew.get(i).getMessage());
        txtDate.setText(DateConverter.toDate(mMessagesnew.get(i).getTimeStamp()).toString());

        return convertView;
    }
}
