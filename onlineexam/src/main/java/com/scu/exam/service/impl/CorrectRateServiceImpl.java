package com.scu.exam.service.impl;

import com.scu.exam.mapper.CorrectRateMapper;
import com.scu.exam.pojo.CorrectRate;
import com.scu.exam.service.CorrectRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorrectRateServiceImpl implements CorrectRateService {

    @Autowired
    private CorrectRateMapper correctRateMapper;

    @Override
    public CorrectRate findByAllId(int question_id, int paper_id) {
        return correctRateMapper.findByAllId(question_id, paper_id);
    }

    @Override
    public List<CorrectRate> findByPid(int paper_id) {
        return correctRateMapper.findByPid(paper_id);
    }

    @Override
    public int insertCorrectRate(CorrectRate correctRate) {
        return correctRateMapper.insertCorrectRate(correctRate);
    }

    @Override
    public int insertCorrectRateBatch(List<CorrectRate> correctRateList) {
        return correctRateMapper.insertCorrectRateBatch(correctRateList);
    }

    @Override
    public int updateCorrectRate(CorrectRate correctRate) {
        return correctRateMapper.updateCorrectRate(correctRate);
    }

    @Override
    public int deleteCorrectRate(int question_id, int paper_id) {
        return correctRateMapper.deleteCorrectRate(question_id, paper_id);
    }

    @Override
    public int deleteCorrectRateBatch(int paper_id) {
        return correctRateMapper.deleteCorrectRateBatch(paper_id);
    }
}
