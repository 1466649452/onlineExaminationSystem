package com.scu.exam.pojo;

public class Question {
     private int question_id;
     private String question_info;
     private String correct_answer;
     private String type;

    public Question(int question_id, String question_info, String correct_answer, String type) {
        this.question_id = question_id;
        this.question_info = question_info;
        this.correct_answer = correct_answer;
        this.type = type;
    }

    public Question() {
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(String question_info) {
        this.question_info = question_info;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question_id=" + question_id +
                ", question_info='" + question_info + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
