package com.scu.exam.pojo;

public class CorrectRate {
    private int question_id;
    private int paper_id;
    private double accuracy;
    private int res_number;
    private int point;

    public CorrectRate(int question_id, int paper_id, double accuracy, int res_number, int point) {
        this.question_id = question_id;
        this.paper_id = paper_id;
        this.accuracy = accuracy;
        this.res_number = res_number;
        this.point = point;
    }

    public CorrectRate() {
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getRes_number() {
        return res_number;
    }

    public void setRes_number(int res_number) {
        this.res_number = res_number;
    }

    @Override
    public String toString() {
        return "CorrectRate{" +
                "question_id=" + question_id +
                ", paper_id=" + paper_id +
                ", accuracy=" + accuracy +
                ", res_number=" + res_number +
                ", point=" + point +
                '}';
    }
}
