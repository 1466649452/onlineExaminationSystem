package com.scu.exam.pojo;

public class Classes {
    private String class_id;
    private Integer grade;
    private String t_id;
    private Integer stu_number;
    private String school;

    public Classes(String class_id, Integer grade, String t_id, Integer stu_number, String school) {
        this.class_id = class_id;
        this.grade = grade;
        this.t_id = t_id;
        this.stu_number = stu_number;
        this.school = school;
    }

    public Classes() {
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public Integer getStu_number() {
        return stu_number;
    }

    public void setStu_number(Integer stu_number) {
        this.stu_number = stu_number;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "class_id='" + class_id + '\'' +
                ", grade=" + grade +
                ", t_id='" + t_id + '\'' +
                ", stu_number=" + stu_number +
                ", school='" + school + '\'' +
                '}';
    }
}

