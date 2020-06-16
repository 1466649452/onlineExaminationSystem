package com.scu.exam.service.impl;

import com.scu.exam.mapper.TeacherMapper;
import com.scu.exam.pojo.Teacher;
import com.scu.exam.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public int insertOneTeacher(Teacher teacher) {
        return teacherMapper.insertOneTeacher(teacher);
    }

    @Override
    public int insertManyTeacher(List<Teacher> teacherList) {
        return teacherMapper.insertManyTeacher(teacherList);
    }

    @Override
    public Teacher findTeacherById(String t_id) {
        return teacherMapper.findTeacherById(t_id);
    }

    @Override
    public List<Teacher> findTeacherByname(String t_name) {
        return teacherMapper.findTeacherByname(t_name);
    }

    @Override
    public int deleteTeacherByid(String t_id) {
        return teacherMapper.deleteTeacherByid(t_id);
    }

    @Override
    public int updateTeacherName(String t_id, String t_name) {
        return teacherMapper.updateTeacherName(t_id,t_name);
    }

    @Override
    public int updateTeacherPassword(String t_id, String t_password) {
        return teacherMapper.updateTeacherPassword(t_id,t_password);
    }

    @Override
    public int updateTeacherHeadimage(String t_id, String t_image) {
        return teacherMapper.updateTeacherHeadimage(t_id,t_image);
    }

    @Override
    public int updateTeacher(String t_id, String t_name, String t_password, String t_image) {
        if(t_image==null){
            t_image="";
        }
        return teacherMapper.updateTeacher(t_id,t_name,t_password,t_image);
    }

    @Override
    public int updateTeacher(String t_id, String t_name, String t_password) {
        return teacherMapper.updateTeacher(t_id,t_name,t_password);
    }
}
