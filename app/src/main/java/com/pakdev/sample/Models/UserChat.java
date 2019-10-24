package com.pakdev.sample.Models;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserChat")

public class UserChat {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "MessageId")
    public String MessageId;

   // public String MessageId;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getRecipientId() {
        return RecipientId;
    }

    public void setRecipientId(String recipientId) {
        RecipientId = recipientId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String Message;
    public String RecipientId;
    public String SenderId;
    public String TimeStamp;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String Type;


}
