package com.scu.exam.service;

import com.scu.exam.pojo.School;

import java.util.List;

public interface SchoolService {
    School findSchoolByName(String school_name);
    int insertSchool(School school);
    int updateSchool(School school);
    int deleteSchoolByName(String school_name);
    List<School> findAllSchool();
}
