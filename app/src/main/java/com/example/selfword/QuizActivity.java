package com.example.selfword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    List<WordEntity> wordList = new ArrayList<>();
    TextView txt_the_word;
    EditText edt_answer;
    Button btn_control_answer,btn_show_wordlength,btn_show_answerhint;
    WordDatabase wordDatabase;
    String str_answer;
    int answer_current_id; //holds the ID(in wordList) of the current answer .

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txt_the_word = (TextView) findViewById(R.id.txt_the_word);
        edt_answer = (EditText) findViewById(R.id.edt_answer);
        btn_control_answer = (Button) findViewById(R.id.btn_control_answer);
        btn_show_wordlength = (Button) findViewById(R.id.btn_show_wordlength);
        btn_show_answerhint = (Button) findViewById(R.id.btn_show_answerhint);

        wordDatabase = WordDatabase.getInstance(this);
        wordList = wordDatabase.wordDao().getAll();

        str_answer = makequiz();
        btn_control_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_answer.getText().toString().toLowerCase().trim().equals(str_answer.toLowerCase().trim())){
                    Toast.makeText(QuizActivity.this, "TRUE", Toast.LENGTH_SHORT).show();
                    String getStrname = wordList.get(answer_current_id).getThe_word();
                    int tmp_status = wordList.get(answer_current_id).getStatus_of_word();
                    if(tmp_status < 5) {
                        tmp_status = tmp_status + 1;
                        wordDatabase.wordDao().updateStatusofWord(wordDatabase.wordDao().getIdofWord(getStrname), tmp_status);
                    }
                }
                else{
                    int tmpstatus = wordList.get(answer_current_id).getStatus_of_word();
                    String getStrname = wordList.get(answer_current_id).getThe_word();
                    Toast.makeText(QuizActivity.this, "FALSE. Correct Answer is: "+str_answer, Toast.LENGTH_SHORT).show();
                    if (tmpstatus>1){
                        tmpstatus = tmpstatus  - 1;
                        wordDatabase.wordDao().updateStatusofWord(wordDatabase.wordDao().getIdofWord(getStrname), tmpstatus);
                    }
                }
                str_answer =  makequiz();
                edt_answer.setText("");
            }

        });
        btn_show_wordlength.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(QuizActivity.this, str_answer.length()+"", Toast.LENGTH_SHORT).show();
            }
        });
        btn_show_answerhint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_answer.setText(str_answer.charAt(0)+"");
            }
        });
    }
    public String makequiz(){
        while (true){
            Random random = new Random();
            int rnd_word_id = random.nextInt(wordList.size());
            if (wordList.get(rnd_word_id).getStatus_of_word() == 5){
                int temp_rnd_word_id = random.nextInt(100);
                if (temp_rnd_word_id<10){
                    txt_the_word.setText(wordList.get(rnd_word_id).getThe_word());
                    String answer = wordList.get(rnd_word_id).getMean_of_word();
                    answer_current_id = rnd_word_id;
                    return answer;
                }
            }
            else {
                txt_the_word.setText(wordList.get(rnd_word_id).getThe_word());
                String answer = wordList.get(rnd_word_id).getMean_of_word();
                answer_current_id = rnd_word_id;
                return answer;
            }
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}