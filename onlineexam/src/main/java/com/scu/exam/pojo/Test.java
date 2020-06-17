package com.scu.exam.pojo;


import java.sql.Timestamp;

public class Test {
    private int paper_id;
    private String paper_name;
    private String t_id;
    private Timestamp start;
    private Timestamp end;

    public Test(int paper_id, String paper_name, String t_id, Timestamp start, Timestamp end) {
        this.paper_id = paper_id;
        this.paper_name = paper_name;
        this.t_id = t_id;
        this.start = start;
        this.end = end;
    }

    public Test() {
    }


    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Test{" +
                "paper_id=" + paper_id +
                ", paper_name='" + paper_name + '\'' +
                ", t_id='" + t_id + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
