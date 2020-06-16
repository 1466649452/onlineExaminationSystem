package com.scu.exam.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.mapper.ScoreMapper;
import com.scu.exam.pojo.Score;
import com.scu.exam.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreMapper scoremapper;

    /*
    * 查询
    * */
    @Override
    public List<Score> findScoreBystuid(String stu_id) {
        return scoremapper.findScoreBystuid(stu_id);
    }

    @Override
    public Score findOneScore(String stu_id, Integer paper_id) {
        return scoremapper.findOneScore(stu_id,paper_id);
    }

    @Override
    public List<Score> findScoreBypaperid(Integer paper_id) {
        return scoremapper.findScoreBypaperid(paper_id);
    }

    //试卷成绩查询范围
    @Override
    public List<Score> findScoreCompare(Integer paper_id, double score, String cmp, String orderseq) {
        return scoremapper.findScoreCompare(paper_id,score,cmp,orderseq);
    }

    @Override
    public List<Score> findScoreCompare(Integer paper_id, double score, String cmp) {
        return scoremapper.findScoreCompare(paper_id,score,cmp);
    }








    /*
    * 插入
    * */
    @Override
    public int insertOneScore(Score score) {
        return scoremapper.insertOneScore(score);
    }

    @Override
    public int insetManyScore(List<Score> scoreList) {
        return scoremapper.insetManyScore(scoreList);
    }


    /*
    * 删除
    * */
    @Override
    public int deleteOneScore(String stu_id, Integer paper_id) {
        return scoremapper.deleteOneScore(stu_id,paper_id);
    }
    @Override
    public int deleteBystuid(String stu_id) {
        return scoremapper.deleteBystuid(stu_id);
    }
    @Override
    public int deleteBypaperid(Integer paper_id) {
        return scoremapper.deleteBypaperid(paper_id);
    }

    /*
    * 更新
    * */
    @Override
    public int updateScore(String stu_id, Integer paper_id, double score) {
        return scoremapper.updateScore(stu_id,paper_id,score);
    }

    @Override
    public int updatefinishTime(String stu_id, Integer paper_id, Date finish) {
        return scoremapper.updatefinishTime(stu_id,paper_id,finish.getTime());
    }

    @Override
    public int updatefinishStuans(String stu_id, Integer paper_id, JSONObject stu_ans) {
        return scoremapper.updatefinishStuans(stu_id,paper_id,stu_ans.toJSONString());
    }

}
