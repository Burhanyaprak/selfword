package com.example.selfword;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Defined table name => word_table
@Entity(tableName = "words_table")
public class WordEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int ID;

    @ColumnInfo(name = "the_word")
    private String the_word;

    @ColumnInfo(name = "mean_of_word")
    private String mean_of_word;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getThe_word() {
        return the_word;
    }

    public void setThe_word(String the_word) {
        this.the_word = the_word;
    }

    public String getMean_of_word() {
        return mean_of_word;
    }

    public void setMean_of_word(String mean_of_word) {
        this.mean_of_word = mean_of_word;
    }
}

