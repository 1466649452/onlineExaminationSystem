package com.scu.exam.mapper;

import com.scu.exam.pojo.Question;

import java.util.List;

public interface QuestionMapper {
    int insert(Question question);

    int deleteById(Integer question_id);

    Question findById(Integer question_id);

    List<Question> findAll();

    int delete(Question question);

    int update(Question question);

    Question findByInfo(String question_info);
}
