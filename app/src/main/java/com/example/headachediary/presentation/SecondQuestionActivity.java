package com.example.headachediary.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TimePicker;

import com.example.headachediary.R;

public class SecondQuestionActivity extends AppCompatActivity {

    private TimePicker tpStar, tpEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_question);
        tpStar = findViewById(R.id.timePickerStart);
        tpStar.setIs24HourView(true);
        tpEnd = findViewById(R.id.timePickerEnd);
        tpEnd.setIs24HourView(true);
    }
}