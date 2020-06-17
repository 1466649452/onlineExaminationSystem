package com.scu.exam.mapper;

import com.scu.exam.pojo.Score;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ScoreMapper {
    /*
    * 查询------开发完成
    * */
    //通过stu_id查多条
    List<Score> findScoreBystuid(String stu_id);
    //通过 stu_id和paper_id查一条
    Score findOneScore(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id);
    //通过paper_id查多条
    List<Score> findScoreBypaperid(Integer paper_id);
    //查询成绩与某一分值的比较,cmp限定为more，equal，less
    List<Score> findScoreCompare(@Param("paper_id")Integer paper_id,@Param("score") double score, @Param("cmp") String cmp,@Param("orderseq") String orderseq);
    List<Score> findScoreCompare(@Param("paper_id")Integer paper_id,@Param("score") double score, @Param("cmp") String cmp);


    /*
    * 插入记录 ----完成
    * */
    int insertOneScore(Score score);
    int insetManyScore(List<Score> scoreList);

    /*
    * 删除
    * */
    //删除一条
    int deleteOneScore(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id);
    int deleteBystuid(String stu_id);
    int deleteBypaperid(Integer paper_id);

    /*
    * 修改
    * */
    int updateScore(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("score") double score);
    int updatefinishTime(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("finish") Long finish);
    int updatefinishStuans(@Param("stu_id") String stu_id,@Param("paper_id") Integer paper_id,@Param("stu_ans") String stu_ans);


}
