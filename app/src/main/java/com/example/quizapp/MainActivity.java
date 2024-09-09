package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Quiz quiz;
    private TextView questionNoView;
    private TextView questionTextView;
    private RadioGroup optionsGroup;
    private Button prevButton, nextButton, revealButton;
    private TextView scoreTextView;
    private TextView timerTextView;

    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        quiz = new Quiz(this);

        questionNoView = findViewById(R.id.questionNoTextView);
        questionTextView = findViewById(R.id.questionTextView);
        optionsGroup = findViewById(R.id.optionsRadioGroup);
        nextButton = findViewById(R.id.next);
        prevButton = findViewById(R.id.previous);
        revealButton = findViewById(R.id.reveal);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timeTextView);

        loadQuestion();
        scoreTextView.setText(getString(R.string.score, quiz.getScore()));
        prevButton.setEnabled(false);

        long durationMillis = 110000;
        long intervalMillis = 1000;

        timer = new CountDownTimer(durationMillis,intervalMillis) {

            @Override
            public void onTick(long millisUntilFinished) {

                long secondsRemaining = millisUntilFinished / 1000;
                long minutes = secondsRemaining / 60;
                long seconds = secondsRemaining % 60;

                timerTextView.setText(getString(R.string.time,String.format("%02d:%02d", minutes, seconds)));
            }

            @Override
            public void onFinish() {
                finishQuiz();
            }
        };


        timer.start();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(quiz.getCurrentQuestionMarkedAnswer() == -1 && !quiz.isRevealed()) {
                    int markedAnswer = getRadioButtonIndex(optionsGroup, optionsGroup.getCheckedRadioButtonId());
                    if (markedAnswer != -1) {
                        quiz.markAnswer(markedAnswer);
                        if (quiz.getCurrentQuestion().getAnswer() == markedAnswer + 1) {
                            quiz.updateScore(5);
                            quiz.incrementCorrectAnswers();
                        } else {
                            quiz.updateScore(-1);
                        }
                        scoreTextView.setText(getString(R.string.score, quiz.getScore()));
                    }
                }

                if(quiz.getCurrentQuestionNo() == 18){
                    nextButton.setText(R.string.finish);
                }
                else{
                    nextButton.setText(R.string.next);
                }

                if(quiz.getCurrentQuestionNo() == 19){
                    finishQuiz();
                }

                quiz.next();
                loadQuestion();


            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quiz.prev();
                loadQuestion();
            }
        });

        revealButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((RadioButton)optionsGroup.getChildAt(quiz.getCurrentQuestion().getAnswer()-1)).setChecked(true);
                quiz.markAnswer(quiz.getCurrentQuestion().getAnswer()-1);
                quiz.setRevealed();
                quiz.updateScore(-1);
                scoreTextView.setText(getString(R.string.score, quiz.getScore()));
                loadQuestion();
            }
        });
    }

    private void loadQuestion() {
        prevButton.setEnabled(quiz.getCurrentQuestionNo() != 0);
        revealButton.setEnabled(true);
        Question currentQuestion = quiz.getCurrentQuestion();
        optionsGroup.clearCheck();
        int markedAnswer = quiz.getCurrentQuestionMarkedAnswer();
        questionNoView.setText(getString(R.string.question_number,quiz.getCurrentQuestionNo()+1,20));
        questionTextView.setText(currentQuestion.getQuestionText());
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            ((RadioButton) optionsGroup.getChildAt(i)).setText(options[i]);
            ((RadioButton) optionsGroup.getChildAt(i)).setEnabled(markedAnswer == -1);
        }
        if(markedAnswer != -1) {
            ((RadioButton) optionsGroup.getChildAt(markedAnswer)).setChecked(true);
            revealButton.setEnabled(false);
        }
    }


    private int getRadioButtonIndex(RadioGroup radioGroup, int checkedRadioButtonId) {
        int count = radioGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                if (child.getId() == checkedRadioButtonId) {
                    return i;
                }
            }
        }
        return -1;
    }

    private void finishQuiz(){
        Intent intent = new Intent(this, Finsish.class);

        intent.putExtra("SCORE", quiz.getScore());
        intent.putExtra("CORRECT_ANSWERS", quiz.getCorrectAnswers());

        startActivity(intent);
        finish();
    }
}