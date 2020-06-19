package com.scu.exam.service.impl;

import com.scu.exam.mapper.QuestionMapper;
import com.scu.exam.pojo.Question;
import com.scu.exam.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public int insertQuestion(Question question) {
        return questionMapper.insert(question);
    }

    @Override
    public int deleteQuestion(Question question) {
        return questionMapper.delete(question);
    }

    @Override
    public int deleteQuestionById(Integer question_id) {
        return questionMapper.deleteById(question_id);
    }

    @Override
    public int updateQuestion(Question question) {
        return questionMapper.update(question);
    }


    @Override
    public Question findQuestionById(Integer question_id) {
        return questionMapper.findById(question_id);
    }

    @Override
    public List<Question> findAllQuestion() {
        return questionMapper.findAll();
    }

    @Override
    public Question findQuestionByInfo(String question_info) {
        return questionMapper.findByInfo(question_info);
    }

    @Override
    public List<Question> findQuestionByKeyword(String keyword) {
        return questionMapper.findByKeyword(keyword);
    }


}
