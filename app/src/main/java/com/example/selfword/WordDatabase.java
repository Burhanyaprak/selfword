package com.example.selfword;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

    private static WordDatabase wordDatabase;

    private static String DATABASE_NAME = "WORDdatabase";

    public synchronized static WordDatabase getInstance(Context context) {
        if (wordDatabase == null) {
            wordDatabase = Room.databaseBuilder(context.getApplicationContext()
                    , WordDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return wordDatabase;
    }

    public abstract WordDao wordDao();
}