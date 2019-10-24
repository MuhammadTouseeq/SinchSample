package com.pakdev.sample.Repository;

import com.pakdev.sample.Models.UserChat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserChat.class}, version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    public abstract ChatDao articleDao();
}
