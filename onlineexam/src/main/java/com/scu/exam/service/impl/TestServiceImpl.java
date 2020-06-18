package com.scu.exam.service.impl;

import com.scu.exam.mapper.TestMapper;
import com.scu.exam.pojo.Test;
import com.scu.exam.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public List<Test> findByTid(String t_id) {
        return testMapper.findByTid(t_id);
    }

    @Override
    public Test findByPid(int p_id) {
        return testMapper.findByPid(p_id);
    }

    @Override
    public int insertTest(Test test) {
        return testMapper.insertTest(test);
    }

    @Override
    public int updateTest(Test test) {
        return testMapper.updateTest(test);
    }

    @Override
    public int deleteTest(int paper_id) {
        return testMapper.deleteTest(paper_id);
    }

    @Override
    public int deleteAllByTid(String t_id) {
        return testMapper.deleteAllByTid(t_id);
    }
}
