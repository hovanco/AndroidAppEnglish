package com.example.androidvocabulary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity implements VocabularyAdapter.OnItemClicked {

    AppDatabase db;
    public Button btn_add;

    RecyclerView recyclerviewVocabulary;
    VocabularyAdapter vocabularyAdapter;
    public static List<Vocabulary> vocabularies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerviewVocabulary = findViewById(R.id.recycler_view_vocabulary);
        recyclerviewVocabulary.setLayoutManager(new LinearLayoutManager((this)));

        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

        btn_add = (Button) findViewById(R.id.btnAdd);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddScreen();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllStudent();
    }

    public void getAllStudent() {
        new AsyncTask<Void, Void, List<Vocabulary>>() {
            @Override
            protected List<Vocabulary> doInBackground(Void... voids) {
                vocabularies = db.vocabularyDao().getAll();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        vocabularyAdapter = new VocabularyAdapter(this, vocabularies);
                        vocabularyAdapter.setOnClick(MainActivity.this);
                        recyclerviewVocabulary.setAdapter(vocabularyAdapter);
                        //Toast.makeText(MainActivity.this, "size" + memories.size(), Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }
        }.execute();
    }

    public void openAddScreen() {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }


    private void openUpdateTodoScreen(Vocabulary vocabulary) {
        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("id", vocabulary.getId());
        intent.putExtra("vocabulary", vocabulary.getVocabulary());
        intent.putExtra("mean", vocabulary.getMean());
        startActivity(intent);
    }


    @Override
    public void onItemDeleteClick(int position) {
        deleteVocabulary(position);
    }

    void deleteVocabulary(final int position){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db.vocabularyDao().delete(vocabularyAdapter.getVocabularies().get(position));
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                vocabularyAdapter.getVocabularies().remove(position);
                vocabularyAdapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void showAlertDelete(int position) {
    }

    @Override
    public void onItemUpdateClick(int position) {
        openUpdateTodoScreen(vocabularies.get(position));

    }
}
