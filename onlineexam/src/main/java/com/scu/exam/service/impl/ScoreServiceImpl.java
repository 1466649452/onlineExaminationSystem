package com.scu.exam.service.impl;

import com.scu.exam.mapper.ScoreMapper;
import com.scu.exam.pojo.Score;
import com.scu.exam.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoremapper;

    @Override
    public Score findOneScore(Integer stu_id) {
        System.out.println("到达service");
        return scoremapper.findOneScore(stu_id);
    }
}
