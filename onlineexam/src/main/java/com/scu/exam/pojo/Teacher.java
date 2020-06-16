package com.scu.exam.pojo;

public class Teacher {
    private String t_id;
    private String t_name;
    private String t_password;
    private String t_image;

    public Teacher(String t_id, String t_name, String t_password, String t_image) {
        this.t_id = t_id;
        this.t_name = t_name;
        this.t_password = t_password;
        if (t_image == null) {
            this.t_image = "";
        } else {
            this.t_image = t_image;
        }
    }

    public Teacher() {

    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_password() {
        return t_password;
    }

    public void setT_password(String t_password) {
        this.t_password = t_password;
    }

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "t_id='" + t_id + '\'' +
                ", t_name='" + t_name + '\'' +
                ", t_password='" + t_password + '\'' +
                ", t_image='" + t_image + '\'' +
                '}';
    }
}
