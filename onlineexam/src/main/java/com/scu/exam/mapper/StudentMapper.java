package com.scu.exam.mapper;

import com.scu.exam.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    //增加
    int insertStudent(Student student);
    int insertStudentList(List<Student> studentList);

    //查询
    Student findStudentById(String stu_id);
    List<Student> findStudentByName(String stu_name);

    //删除
    int deleteStudentById(String stu_id);

    //修改
    int updateStudent(Student student);
}
