package com.mapper;

import com.pojo.Answer;

import java.util.List;

public interface AnswerMapper {

    int deleteById(Integer question_id);

    Answer findById(Integer question_id);

    int insert(Answer answer);

    int delete(Answer answer);

    int update(Answer answer);

    List<Answer> find();
}
