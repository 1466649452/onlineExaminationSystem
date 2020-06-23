package com.scu.exam.service.impl;

import com.scu.exam.mapper.ClassesMapper;
import com.scu.exam.pojo.Classes;
import com.scu.exam.service.ClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public Classes findClassById(String class_id) {
        return classesMapper.findClassById(class_id);
    }

    @Override
    public int insertClass(Classes classes) {
        return classesMapper.insertClass(classes);
    }

    @Override
    public int updateClass(Classes classes) {
        return classesMapper.updateClass(classes);
    }

    @Override
    public int deleteClassById(String class_id) {
        return classesMapper.deleteClassById(class_id);
    }

    @Override
    public List<Classes> findClassBySchool(String school) {
        return classesMapper.findClassBySchool(school);
    }
}
