package com.example.androidvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    AppDatabase db;
    private String vocabulary, mean;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        buttonAdd = (Button) findViewById(R.id.btn_add);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addVocabulary();
                finish();
            }
        });
    }

    private void addVocabulary() {
        final EditText editVocabulary = (EditText) findViewById(R.id.edit_vocabulary);
        final EditText editMean = (EditText) findViewById(R.id.edit_mean);

        vocabulary = editVocabulary.getText().toString();
        mean = editMean.getText().toString();

        if (vocabulary.isEmpty() || mean.isEmpty()) {
            Toast.makeText(this, "Data must not null", Toast.LENGTH_SHORT).show();
            return;
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Vocabulary newVocabulary = new Vocabulary();
                newVocabulary.setVocabulary(vocabulary);
                newVocabulary.setMean(mean);
                db.vocabularyDao().insert(newVocabulary);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(AddActivity.this, vocabulary + " has been added successfully", Toast.LENGTH_SHORT).show();

            }
        }.execute();
    }
}
