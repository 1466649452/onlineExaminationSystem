package com.scu.exam.mapper;

import com.scu.exam.pojo.Score;
import org.mapstruct.Mapper;

@Mapper
public interface ScoreMapper {
    Score findOneScore(Integer stu_id);
}
