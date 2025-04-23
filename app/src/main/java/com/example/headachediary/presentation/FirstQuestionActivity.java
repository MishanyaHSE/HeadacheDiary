package com.example.headachediary.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.headachediary.R;
import com.example.headachediary.domain.headache.SurveyData;

public class FirstQuestionActivity extends AppCompatActivity {

    SurveyData sd = new SurveyData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_question);
    }


    public void nextQuestion(View view) {
        sd.isHeadache = true;
        Intent intent = new Intent(this, SecondQuestionActivity.class);
        startActivity(intent);
    }

    public void goToMainMenu(View view) {
        sd.isHeadache = false;
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }
}