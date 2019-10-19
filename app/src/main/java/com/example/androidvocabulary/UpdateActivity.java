package com.example.androidvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    int vocabularyId;
    EditText editTextUpdateVocabulary, editTextUpdateMean;
    Button ButtonUpdate;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        editTextUpdateVocabulary = findViewById(R.id.edt_update_vocabulary);
        editTextUpdateMean = findViewById(R.id.edt_update_mean);
        ButtonUpdate = findViewById(R.id.button_update);

        vocabularyId= getIntent().getIntExtra("id",0);

        String vocabulary = getIntent().getStringExtra("vocabulary");
        String mean = getIntent().getStringExtra("vocabulary");

        editTextUpdateVocabulary.setText(vocabulary);
        editTextUpdateMean.setText(mean);

        //kich vao nut update
        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTodoToDatabase();
            }
        });
    }

    //thuc hien chuc nang update
    private void updateTodoToDatabase() {
        final String vocabulary = editTextUpdateVocabulary.getText().toString();
        final String mean = editTextUpdateMean.getText().toString();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Vocabulary newVocabulary = new Vocabulary();
                newVocabulary.setVocabulary(vocabulary);
                newVocabulary.setMean(mean);
                newVocabulary.setId(vocabularyId); // thinking about why we need to set id here

                db.vocabularyDao().update(newVocabulary);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                showSuccessDialog();
            }
        }.execute();
    }

    // Xuat hien dialog
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage("Update Success")
                .setPositiveButton("Got it", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();
    }

}
