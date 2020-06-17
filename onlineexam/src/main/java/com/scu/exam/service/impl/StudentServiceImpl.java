package com.scu.exam.service.impl;

import com.scu.exam.mapper.StudentMapper;
import com.scu.exam.pojo.Student;
import com.scu.exam.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int insertStudent(Student student) {
        return studentMapper.insertStudent(student);
    }

    @Override
    public int insertStudentList(List<Student> studentList) {
        return studentMapper.insertStudentList(studentList);
    }

    @Override
    public Student findStudentById(String stu_id) {
        return studentMapper.findStudentById(stu_id);
    }

    @Override
    public List<Student> findStudentByName(String stu_name) {
        return studentMapper.findStudentByName(stu_name);
    }

    @Override
    public int deleteStudentById(String stu_id) {
        return studentMapper.deleteStudentById(stu_id);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }
}
