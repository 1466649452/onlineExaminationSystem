package com.scu.exam.mapper;

import com.scu.exam.pojo.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    //根据老师的id查看其所发布的所有试卷
    //如果查询不到数据会返回list.size=0
    List<Test> findByTid(String t_id);

    //插入试卷
    int insertTest(Test test);

    //修改试卷
    int updateTest(Test test);

    //删除某张试卷
    int deleteTest(int paper_id);

    //删除某老师出的所有卷子
    int deleteAllByTid(String t_id);

}
