package com.example.androidvocabulary;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Vocabulary {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String vocabulary;

    private String mean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public boolean getDescription() {
        return getDescription();
    }
}
