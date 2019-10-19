package com.example.androidvocabulary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Vocabulary.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract VocabularyDao vocabularyDao();
}