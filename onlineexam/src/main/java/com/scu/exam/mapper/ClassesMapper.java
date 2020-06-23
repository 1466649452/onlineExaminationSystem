package com.scu.exam.mapper;

import com.scu.exam.pojo.Classes;
import com.scu.exam.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassesMapper {
    Classes findClassById(String class_id);
    int insertClass(Classes classes);
    int updateClass(Classes classes);
    int deleteClassById(String class_id);
    List<Classes> findClassBySchool(String school);
}
