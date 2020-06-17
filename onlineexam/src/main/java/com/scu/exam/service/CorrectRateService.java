package com.scu.exam.service;

import com.scu.exam.pojo.CorrectRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CorrectRateService {

    //查询试卷上某道题的正确率、答题人数、分值
    CorrectRate findByAllId(@Param("question_id") int question_id, @Param("paper_id") int paper_id);

    //查询卷子上所有题目的id,正确率、答题人数、分值
    List<CorrectRate> findByPid(int paper_id);

    //单条插入
    int insertCorrectRate(CorrectRate correctRate);

    //批量插入
    int insertCorrectRateBatch(List<CorrectRate> correctRateList);

    int updateCorrectRate(CorrectRate correctRate);

    //删除试卷上的某道题
    int deleteCorrectRate(@Param("question_id") int question_id, @Param("paper_id") int paper_id);

    //删除整张卷子
    int deleteCorrectRateBatch(int paper_id);



}
