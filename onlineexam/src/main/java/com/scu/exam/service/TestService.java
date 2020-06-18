package com.scu.exam.service;

import com.scu.exam.pojo.Test;

import java.util.List;

public interface TestService {

    //根据老师的id查看其所发布的所有试卷
    List<Test> findByTid(String t_id);

    //根据试卷id查找试卷
    Test findByPid(int p_id);

    //插入试卷
    int insertTest(Test test);

    //修改试卷
    int updateTest(Test test);

    //删除试卷
    int deleteTest(int paper_id);

    //删除某老师出的所有卷子
    int deleteAllByTid(String t_id);
}
