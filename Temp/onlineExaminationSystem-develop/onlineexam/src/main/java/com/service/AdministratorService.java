package com.service.implement;

import com.mapper.AnswerMapper;
import com.pojo.Answer;
import com.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerMapper answerMapper;


    @Override
    public int insertAnswer(Answer answer) {
        return answerMapper.insert(answer);
    }

    @Override
    public int deleteAnswerById(Integer question_id) {
        return answerMapper.deleteById(question_id);
    }

    @Override
    public int updateAnswerById(Integer question_id) {
        return answerMapper.updateById(question_id);
    }

    @Override
    public Answer findAnswerById(Integer question_id) {
        return answerMapper.findById(question_id);
    }
}
                                                                                                                                                                     