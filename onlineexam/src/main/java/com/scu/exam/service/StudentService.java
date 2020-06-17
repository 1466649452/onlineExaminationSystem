package com.scu.exam.service;

import com.scu.exam.pojo.Student;

import java.util.List;

public interface StudentService {

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
