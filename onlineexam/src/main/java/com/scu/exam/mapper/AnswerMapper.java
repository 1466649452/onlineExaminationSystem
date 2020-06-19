package com.scu.exam.mapper;

import com.scu.exam.pojo.Answer;

import java.util.List;

public interface AnswerMapper {

    int deleteById(Integer question_id);

    List<Answer> findById(Integer question_id);

    int insert(Answer answer);

    int delete(Answer answer);

    int update(Answer answer);

    List<Answer> find();
}
