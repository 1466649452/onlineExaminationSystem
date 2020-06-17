package com.scu.exam.service.impl;

import com.scu.exam.mapper.AnswerMapper;
import com.scu.exam.pojo.Answer;
import com.scu.exam.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public int insertAnswer(Answer answer) {
        return answerMapper.insert(answer);
    }

    @Override
    public int deleteAnswer(Answer answer) {
        return answerMapper.delete(answer);
    }

    @Override
    public int deleteAnswerById(Integer question_id) {
        return answerMapper.deleteById(question_id);
    }

    @Override
    public int updateAnswer(Answer answer) {
        return answerMapper.update(answer);
    }

    @Override
    public Answer findAnswerById(Integer question_id) {
        return answerMapper.findById(question_id);
    }

    @Override
    public List<Answer> findAllAnswer() {
        return answerMapper.find();
    }
}
