package com.scu.exam.pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Score {
    private String stu_id;
    private int paper_id;
    private float score;
    private Date finish;

    /**
     * hashmaphashMap中key为quesiton_id
     */
    private Map stu_ans=new HashMap();

    //构造器
    public Score(String stu_id, int paper_id, float score, Date finish, Map stu_ans) {
        this.stu_id = stu_id;
        this.paper_id = paper_id;
        this.score = score;
        this.finish = finish;
        this.stu_ans = stu_ans;
    }
    public Score(){

    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Map getStu_ans() {
        return stu_ans;
    }

    public void setStu_ans(Map stu_ans) {
        this.stu_ans = stu_ans;
    }

    @Override
    public String toString() {
        return "Score{" +
                "stu_id='" + stu_id + '\'' +
                ", paper_id=" + paper_id +
                ", score=" + score +
                ", finish=" + finish +
                ", stu_ans=" + stu_ans +
                '}';
    }
}
