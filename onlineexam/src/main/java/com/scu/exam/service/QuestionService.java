package com.scu.exam.service;

import com.scu.exam.pojo.Question;

import java.util.List;

public interface QuestionService {
    /*
    添加试题到数据库中
    输入：试题
     */
    int insertQuestion(Question question);

    /*
    删除指定的试题
    输入：试题
     */
    int deleteQuestion(Question question);

    /*
    删除指定id的试题
    输入：试题的id
     */
    int deleteQuestionById(Integer question_id);

    /*
    修改指定试题的信息
    输入：试题
     */
    int updateQuestion(Question question);

    /*
    查找指定id的试题
    输入：试题的id
    输出：查找到的试题
     */
    Question findQuestionById(Integer question_id);

    /*
    查找所有的试题
    输入：无
    输出：试题列表
     */
    List<Question> findAllQuestion();

    Question findQuestionByInfo(String question_info);

    List<Question> findQuestionByKeyword(String keyword);
}
