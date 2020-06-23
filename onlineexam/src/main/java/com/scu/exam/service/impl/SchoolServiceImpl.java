package com.scu.exam.service.impl;

import com.scu.exam.mapper.SchoolMapper;
import com.scu.exam.pojo.School;
import com.scu.exam.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public School findSchoolByName(String school_name) {
        return schoolMapper.findSchoolByName(school_name);
    }

    @Override
    public int insertSchool(School school) {
        return schoolMapper.insertSchool(school);
    }

    @Override
    public int updateSchool(School school) {
        return schoolMapper.updateSchool(school);
    }

    @Override
    public int deleteSchoolByName(String school_name) {
        return schoolMapper.deleteSchoolByName(school_name);
    }

    @Override
    public List<School> findAllSchool() {
        return schoolMapper.findAllSchool();
    }
}
