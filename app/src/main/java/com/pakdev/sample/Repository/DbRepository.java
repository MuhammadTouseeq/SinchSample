package com.pakdev.sample.Repository;


import android.content.Context;
import android.os.AsyncTask;

import com.pakdev.sample.Models.UserChat;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

public class DbRepository {

    ChatDao chatdao;

    Context c;
    AppDB db;


    public DbRepository(Context application) {
        this.c = application;
        db = Room.databaseBuilder(c, AppDB.class, "userdb").allowMainThreadQueries().build();
        chatdao = db.articleDao();
    }


    public List<UserChat> getAllChat() {

        return chatdao.getallChat();

    }


    public void deleteAllitems() {
        new deleteAsync(chatdao).execute();

    }

    public void insertItems(UserChat msg) {
        new insertAsyncTask(chatdao).execute(msg);
    }

    private static class insertAsyncTask extends AsyncTask<UserChat, Void, Void> {

        private ChatDao mAsyncTaskDao;

        insertAsyncTask(ChatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final UserChat... params) {
            mAsyncTaskDao.insertItem(params[0]);
            return null;
        }
    }

    private static class deleteAsync extends AsyncTask<Void, Void, Void> {

        private ChatDao mAsyncTaskDao;

        deleteAsync(ChatDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAsyncTaskDao.delete();
            return null;
        }
    }


}
