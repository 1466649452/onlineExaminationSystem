package com.service.implement;

import com.mapper.QuestionMapper;
import com.pojo.Question;
import com.service.QuestionService;
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
}
