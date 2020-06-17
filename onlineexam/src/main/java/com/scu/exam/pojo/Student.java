package com.scu.exam.pojo;

import java.sql.Date;

public class Student {
    private String stu_id;
    private String stu_name;
    private String stu_password;
    private String class_id;
    private String stu_Image;
    private String stu_idcard;
    private java.util.Date stu_birthdate;

    public Student() {
    }

    public Student(String stu_id, String stu_name, String stu_password, String class_id, String stu_Image, String stu_idcard, Date stu_birthdate) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_password = stu_password;
        this.class_id = class_id;
        this.stu_Image = stu_Image;
        this.stu_idcard = stu_idcard;
        this.stu_birthdate = stu_birthdate;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_password() {
        return stu_password;
    }

    public void setStu_password(String stu_password) {
        this.stu_password = stu_password;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getStu_Image() {
        return stu_Image;
    }

    public void setStu_Image(String stu_Image) {
        this.stu_Image = stu_Image;
    }

    public String getStu_idcard() {
        return stu_idcard;
    }

    public void setStu_idcard(String stu_idcard) {
        this.stu_idcard = stu_idcard;
    }

    public java.util.Date getStu_birthdate() {
        return stu_birthdate;
    }

    public void setStu_birthdate(java.util.Date stu_birthdate) {
        this.stu_birthdate = stu_birthdate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stu_id='" + stu_id + '\'' +
                ", stu_name='" + stu_name + '\'' +
                ", stu_password='" + stu_password + '\'' +
                ", class_id='" + class_id + '\'' +
                ", stu_Image='" + stu_Image + '\'' +
                ", stu_idcard='" + stu_idcard + '\'' +
                ", stu_birthdate=" + stu_birthdate +
                '}';
    }
}