package com.example.quiz;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quiz.data.Storage;
import com.example.quiz.data.StorageSharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RadioGroup Q1,Q2,Q3,Q4,Q5;
    private Button btnCheck,btnRestart;
    private TextView txtLastResult;
    Map<RadioGroup,Integer> answers = new HashMap<>();
    List<RadioGroup> Questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Q1 = findViewById(R.id.Q1answers);
        Q2 = findViewById(R.id.Q2answers);
        Q3 = findViewById(R.id.Q3answers);
        Q4 = findViewById(R.id.Q4answers);
        Q5 = findViewById(R.id.Q5answers);

        Q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAnswer(Q1);
            }
        });
        Q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAnswer(Q2);
            }
        });
        Q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAnswer(Q3);
            }
        });
        Q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAnswer(Q4);
            }
        });
        Q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAnswer(Q5);
            }
        });

        btnCheck = findViewById(R.id.btnCheck);
        btnRestart = findViewById(R.id.btnRestart);
        txtLastResult = findViewById(R.id.last_result);

        Storage storage = new StorageSharedPreference();
        if(storage.get(this,"last_result") == null)
            storage.save(this,"last_result","-1");

        String lastres = storage.get(this,"last_result");
        if(lastres.equals("-1"))
            txtLastResult.setText(String.format("შედეგი არ არის"));
        else
            txtLastResult.setText(String.format(" %s/5",lastres));

        fillList();
        fillMap();
    }


    void checkAnswer(RadioGroup Rgrp)
    {
        RadioButton tmp = findViewById(Rgrp.getCheckedRadioButtonId());
        if(answers.get(Rgrp) == Rgrp.indexOfChild(tmp))
            tmp.setBackgroundColor(Color.GREEN);
        else
            tmp.setBackgroundColor(Color.RED);
        for(int i = 0;i<Rgrp.getChildCount();i++){
            Rgrp.getChildAt(i).setEnabled(false);
        }
    }

    void UnlockRadbtn(){
        for(int i = 0;i<Questions.size();i++){
            RadioButton r = findViewById(Questions.get(i).getCheckedRadioButtonId());
            if(r != null)
                r.setChecked(false);
            for(int j = 0;j<3;j++){
                Questions.get(i).getChildAt(j).setEnabled(true);
                Questions.get(i).getChildAt(j).setBackgroundColor(Color.WHITE);
            }
        }
    }

    public void CheckResult(View view) {
        int correctanswers = 0;
        for(int i = 0;i<Questions.size();i++){
            RadioButton tmp = findViewById(Questions.get(i).getCheckedRadioButtonId());
            if(answers.get(Questions.get(i)) == Questions.get(i).indexOfChild(tmp))
                correctanswers++;
        }
        Toast.makeText(getApplicationContext(),String.format("სწორი პასუხების რაოდენობა : %d",correctanswers), Toast.LENGTH_SHORT).show();

        Storage storage = new StorageSharedPreference();
        storage.save(this,"last_result", Integer.toString(correctanswers));
        txtLastResult.setText(String.format(" %d/5",correctanswers));
        UnlockRadbtn();
    }

    public void Restart(View view) {
        UnlockRadbtn();
    }

    void fillList(){
        Questions.add(Q1);
        Questions.add(Q2);
        Questions.add(Q3);
        Questions.add(Q4);
        Questions.add(Q5);
    }

    void fillMap(){
        answers.put(Q1,1);
        answers.put(Q2,0);
        answers.put(Q3,2);
        answers.put(Q4,1);
        answers.put(Q5,0);
    }
}
