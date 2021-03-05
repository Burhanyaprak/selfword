package com.example.selfword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText_from_Word, editText_to_Word;
    Button btn_add_word, btn_reset_words, btn_word_quiz;
    RecyclerView recyclerView_words;

    List<WordEntity> wordList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    WordDatabase wordDatabase;
    WordAdapter wordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_from_Word = findViewById(R.id.edit_the_word);
        editText_to_Word = findViewById(R.id.edit_mean_of_word);

        btn_add_word = findViewById(R.id.btn_word_add);
        btn_reset_words = findViewById(R.id.btn_words_reset);
        btn_word_quiz = findViewById(R.id.btn_word_quiz);

        recyclerView_words = findViewById(R.id.recycler_view_words);

        wordDatabase = WordDatabase.getInstance(this);

        wordList = wordDatabase.wordDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView_words.setLayoutManager(linearLayoutManager);

        wordAdapter = new WordAdapter(MainActivity.this, wordList);

        recyclerView_words.setAdapter(wordAdapter);

        btn_add_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = editText_from_Word.getText().toString().trim();
                String sMean = editText_to_Word.getText().toString().trim();

                if (!sText.equals("")) {
                    WordEntity data = new WordEntity();
                    data.setThe_word(sText);
                    data.setMean_of_word(sMean);
                    data.setStatus_of_word(2);
                    wordDatabase.wordDao().insert(data);


                    editText_from_Word.setText("");
                    editText_to_Word.setText("");

                    wordList.clear();
                    wordList.addAll(wordDatabase.wordDao().getAll());
                    wordAdapter.notifyDataSetChanged();
                }
            }
        });
        btn_reset_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordDatabase.wordDao().reset(wordList);
                wordList.clear();
                wordList.addAll(wordDatabase.wordDao().getAll());
                wordAdapter.notifyDataSetChanged();
            }
        });
        btn_word_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }
}