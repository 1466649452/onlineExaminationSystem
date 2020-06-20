package com.scu.exam.pojo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.util.Json;

import java.util.Date;

public class Score {
    private String stu_id;
    private int paper_id;
    private double stu_score;
    private Long finish;
    private String stu_ans;



    //构造器
    public Score(String stu_id, int paper_id, double score, Date finish, JSONObject stu_ans) {
        this.stu_id = stu_id;
        this.paper_id = paper_id;
        this.stu_score = score;
        this.finish = finish.getTime();
        if(stu_ans==null){
            this.stu_ans=new JSONObject().toJSONString();
        }else{
            this.stu_ans = stu_ans.toString();
        }
    }
    public Score(String stu_id, Integer paper_id, Double score, Long finish, JSONObject stu_ans) {
        this.stu_id = stu_id;
        this.paper_id = paper_id;
        this.stu_score = score;
        this.finish = finish;
        if(stu_ans==null){
            this.stu_ans=new JSONObject().toJSONString();
        }else{
            this.stu_ans = stu_ans.toString();
        }
    }
    //构造器
    public Score(String stu_id, int paper_id, double score, Long finish, JSONObject stu_ans) {
        this.stu_id = stu_id;
        this.paper_id = paper_id;
        this.stu_score = score;
        this.finish = finish;
        if(stu_ans==null){
            this.stu_ans=new JSONObject().toJSONString();
        }else{
            this.stu_ans = stu_ans.toString();
        }
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

    public double getScore() {
        return stu_score;
    }

    public void setScore(double score) {
        this.stu_score = score;
    }

    public Date getFinish() {
        return new Date(finish);
    }

    public void setFinish(Date finish) {
        this.finish = finish.getTime();
    }

    public JSONObject getStu_ansObject() {
        return JSONObject.parseObject(this.stu_ans);
    }

    public String getStu_ans() {
        return this.stu_ans;
    }

    public void setStu_ans(String stu_ans) {
        this.stu_ans = stu_ans;
    }

    public void setStu_ansObject(JSONObject stu_ans) {
        this.stu_ans = stu_ans.toString();
    }

    @Override
    public String toString() {
        return "Score{" +
                "stu_id='" + stu_id + '\'' +
                ", paper_id=" + paper_id +
                ", score=" + stu_score +
                ", finish=" + finish +
                ", stu_ans=" + stu_ans +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Score other = (Score) obj;
        if (other.toString() == null) {
            return false;
        }
        if (this.toString().equals(other.toString())) {
           return true;
        }
        return true;
    }
}
