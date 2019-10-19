package com.example.androidvocabulary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VocabularyDao {
    @Query("SELECT * FROM vocabulary")
    List<Vocabulary> getAll();

    @Insert
    void insert(Vocabulary vocabulary);

    @Update
    void update(Vocabulary vocabulary);

    @Delete
    void delete(Vocabulary vocabulary);

}
