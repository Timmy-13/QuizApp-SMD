package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Finsish extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finsish);
        Intent intent = getIntent();
        int score = intent.getIntExtra("SCORE", 0); // Default value is 0
        int correctAnswers = intent.getIntExtra("CORRECT_ANSWERS", 0); // Default value is 0

        TextView scoreTextView = findViewById(R.id.score);
        TextView correctAnswersTextView = findViewById(R.id.percentage);

        scoreTextView.setText(getString(R.string.score,score));
        correctAnswersTextView.setText(getString(R.string.percentage,correctAnswers,20));
    }
}