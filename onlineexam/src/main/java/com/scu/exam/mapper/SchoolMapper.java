package com.scu.exam.mapper;

import com.scu.exam.pojo.School;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolMapper {
    //查找
    School findSchoolByName(String school_name);

    //增加
    int insertSchool(School school);

    //修改
    int updateSchool(School school);

    //删除
    int deleteSchoolByName(String school_name);
}
