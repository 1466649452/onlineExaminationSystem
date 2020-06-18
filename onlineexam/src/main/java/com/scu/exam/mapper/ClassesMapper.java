package com.scu.exam.mapper;

import com.scu.exam.pojo.Classes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassesMapper {
    Classes findClassById(String class_id);
    int insertClass(Classes classes);
    int updateClass(Classes classes);
    int deleteClassById(String class_id);
}
