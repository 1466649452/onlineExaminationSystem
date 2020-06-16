package com.scu.exam.service;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Score;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ScoreService {
    List<Score> findScoreBystuid(String stu_id);
    Score findOneScore(String stu_id,Integer paper_id);
    List<Score> findScoreBypaperid(Integer paper_id);

    List<Score> findScoreCompare(@Param("试卷id")Integer paper_id, @Param("成绩") double score, @Param("可填more,less,equal") String cmp, @Param("可选desc,asc") String orderseq);
    List<Score> findScoreCompare(@Param("试卷id")Integer paper_id,@Param("成绩") double score, @Param("可填more,less,equal") String cmp);


    int insertOneScore(Score score);
    int insetManyScore(List<Score> scoreList);

    int deleteOneScore(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id);
    int deleteBystuid(String stu_id);
    int deleteBypaperid(Integer paper_id);

    int updateScore(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("score") double score);
    int updatefinishTime(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("date类型时间") Date finish);
    int updatefinishStuans(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("JSONobject的json格式") JSONObject stu_ans);

}
