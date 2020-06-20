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
        try {
            return teacherMapper.insertOneTeacher(teacher);
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public int insertManyTeacher(List<Teacher> teacherList) {
        try {
            return teacherMapper.insertManyTeacher(teacherList);
        } catch (Exception e) {
            return 0;
        }

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
        try {
            return teacherMapper.deleteTeacherByid(t_id);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateTeacherName(String t_id, String t_name) {
        try {
            return teacherMapper.updateTeacherName(t_id, t_name);
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public int updateTeacherPassword(String t_id, String t_password) {
        try {
            return teacherMapper.updateTeacherPassword(t_id, t_password);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateTeacherHeadimage(String t_id, String t_image) {
        try {
            return teacherMapper.updateTeacherHeadimage(t_id, t_image);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateTeacher(String t_id, String t_name, String t_password, String t_image) {
        try {
            if (t_image == null) {
                t_image = "";
            }
            return teacherMapper.updateTeacher(t_id, t_name, t_password, t_image);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int updateTeacher(String t_id, String t_name, String t_password) {
        try {
            return teacherMapper.updateTeacher(t_id, t_name, t_password);
        } catch (Exception e) {
            return 0;
        }
    }
}
