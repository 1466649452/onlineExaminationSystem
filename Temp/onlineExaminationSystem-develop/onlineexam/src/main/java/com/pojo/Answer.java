package com.pojo;

public class Answer {
    /*
    题目id:主键，非空
     */
    private Integer question_id;

    /*
    题目答案：主键，非空
     */
    private String answer;

    public Answer(Integer question_id, String answer) {
        this.question_id = question_id;
        this.answer = answer;
    }

    public Answer() {
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question_id=" + question_id +
                ", answer='" + answer + '\'' +
                '}';
    }
}
