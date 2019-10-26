package com.pakdev.sample.activity.messaging;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pakdev.sample.Models.UserChat;
import com.pakdev.sample.R;
import com.pakdev.sample.Repository.DateConverter;

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

        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        return mMessagesnew.get(position).getType();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        //ViewH
        int direction = getItemViewType(i);
        Log.d("##", "this is : " + direction);
        int res = 0;

        if (convertView == null) {

            if (direction == DIRECTION_INCOMING) {
                res = R.layout.item_chat_right;

            } else if (direction == DIRECTION_OUTGOING) {
                res = R.layout.item_chat_left;

            }
            convertView = mInflater.inflate(res, viewGroup, false);


        }


        TextView txtMessage = convertView.findViewById(R.id.txtMessage);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

//        txtSender.setText(name);
        txtMessage.setText(mMessagesnew.get(i).getMessage());
        txtDate.setText(DateConverter.toDate(mMessagesnew.get(i).getTimeStamp()).toString());

        return convertView;
    }
}
