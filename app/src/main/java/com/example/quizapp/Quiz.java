package com.example.quizapp;

import android.content.Context;

public class Quiz {
    private Context context;
    private int currentQuestion;
    private int score;
    private Question[] questions;
    private String time;
    private int[] answeredQuestion;
    private boolean[] revealed;

    private int correctAnswered;


    public Quiz(Context context){
        this.context = context;
        this.score = 0;
        this.time = "00:00";

        int questionBank = Integer.parseInt(context.getString(R.string.questionbank));
        this.questions = new Question[questionBank];
        this.answeredQuestion = new int[questionBank];
        this.revealed = new boolean[questionBank];
        String[] questions = context.getResources().getStringArray(R.array.questions);
        String[] options = new String[4];
        String optionResource;
        int optionResId;
        int[] answers = context.getResources().getIntArray(R.array.answerbook);

        for(int i=0; i<questionBank;i++){
            optionResource = "options" + (i+1);
            optionResId = context.getResources().getIdentifier(optionResource,"array", context.getPackageName());
            options = context.getResources().getStringArray(optionResId);

            this.questions[i] = new Question(questions[i],options,answers[i]);
            this.answeredQuestion[i] = -1;
            this.revealed[i] = false;
            this.correctAnswered = 0;
        }

        this.currentQuestion = 0;
    }

    public Question getCurrentQuestion(){
        return this.questions[this.currentQuestion];
    }

    public void markAnswer(int i){
        this.answeredQuestion[currentQuestion] = i;
    }
    public int getCurrentQuestionMarkedAnswer(){
        return this.answeredQuestion[this.currentQuestion];
    }

    public void next(){
        if(currentQuestion < questions.length - 1) {
            this.currentQuestion += 1;
        }
    }

    public void prev(){
        if(currentQuestion > 0) {
            this.currentQuestion -= 1;
        }
    }

    public int getCurrentQuestionNo(){
        return currentQuestion;
    }

    public void updateScore(int s){
        this.score += s;
    }

    public int getScore(){
        return this.score;
    }

    public void setRevealed() {
        this.revealed[currentQuestion] = true;
    }
    public boolean isRevealed() {
        return this.revealed[currentQuestion];
    }

    public int getCorrectAnswers(){
        return this.correctAnswered;
    }

    public void incrementCorrectAnswers(){
        this.correctAnswered+=1;
    }

}
