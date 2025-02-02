package com.scu.exam.service;

import com.scu.exam.pojo.Classes;

import java.util.List;


public interface ClassesService {
    Classes findClassById(String class_id);
    int insertClass(Classes classes);
    int updateClass(Classes classes);
    int deleteClassById(String class_id);
    List<Classes> findClassBySchool(String school);
}
