package com.example.selfword;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WordEntity wordEntity);

    @Delete
    void delete(WordEntity wordEntity);

    @Delete
    void reset(List<WordEntity> wordEntity);

    @Query("UPDATE words_table SET the_word = :theword, mean_of_word = :meanofword WHERE ID = :sID")
    void update(int sID , String theword, String meanofword);

    @Query("UPDATE words_table SET status_of_word = :statusofword WHERE ID = :sID")
    void updateStatusofWord(int sID, int statusofword);

    @Query("SELECT * FROM words_table")
    List<WordEntity> getAll();

    @Query("SELECT ID FROM words_table WHERE the_word = :theword")
    int getIdofWord(String theword);
}









