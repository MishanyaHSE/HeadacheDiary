package com.example.headachediary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PdfDocument pdf = new PdfDocument();
    }

    public void startSurvey(View view) {
        Intent intent = new Intent(this, FirstQuestionActivity.class);
        startActivity(intent);
    }
}