package com.pakdev.sample.Repository;

import com.pakdev.sample.Models.UserChat;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface ChatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(UserChat items);

    @Query("SELECT * from UserChat order by id ASC")
    List<UserChat> getallChat();

    @Query("DELETE FROM UserChat")
    void delete();

    @Query("SELECT count(*) from UserChat where MessageId=:messageid")
    int getMessageCount(String messageid);
}

