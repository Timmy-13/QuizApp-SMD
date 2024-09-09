package com.example.quizapp;

public class Question {
    private String question;
    private String[] options = new String[4];
    private int answer;

    public Question(String question, String[] options, int answer){
        this.question = question;
        System.arraycopy(options, 0, this.options, 0, options.length);
        this.answer = answer;
    }

    public int getAnswer(){
        return answer;
    }

    public String getQuestionText(){
        return question;
    }

    public String[] getOptions(){
        return options;
    }

}
