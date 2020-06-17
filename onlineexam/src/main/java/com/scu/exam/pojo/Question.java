package com.scu.exam.pojo;

public class Question {
    private Integer question_id;
    private String question_idfo;
    private String correct_answer;
    private String type;

    public Question(Integer question_id, String question_idfo, String correct_answer, String type) {
        this.question_id = question_id;
        this.question_idfo = question_idfo;
        this.correct_answer = correct_answer;
        this.type = type;
    }

    public Question() {
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_idfo() {
        return question_idfo;
    }

    public void setQuestion_idfo(String question_idfo) {
        this.question_idfo = question_idfo;
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
                ", question_idfo='" + question_idfo + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
