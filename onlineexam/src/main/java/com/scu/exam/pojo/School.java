package com.scu.exam.pojo;

public class School {
    private String school_name;
    private String school_address;
    private String school_phone;

    public School(String school_name, String school_address, String school_phone) {
        this.school_name = school_name;
        this.school_address = school_address;
        this.school_phone = school_phone;
    }

    public School() {
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_phone() {
        return school_phone;
    }

    public void setSchool_phone(String school_phone) {
        this.school_phone = school_phone;
    }

    @Override
    public String toString() {
        return "School{" +
                "school_name='" + school_name + '\'' +
                ", school_address='" + school_address + '\'' +
                ", school_phone='" + school_phone + '\'' +
                '}';
    }
}
