package com.service;

import com.pojo.Answer;

import java.util.List;

public interface AnswerService {
    /*
    添加答案到数据库中
     */
    public int insertAnswer(Answer answer);

    /*
    从数据库中删除指定id的答案
    输入：答案所对应的题目id
     */
    public int deleteAnswer(Answer answer);

    /*
    从数据库中删除指定id的答案
    输入：答案所对应的题目id
     */
    public int deleteAnswerById(Integer question_id);

    /*
    从数据库中修改指定的答案
    输入：修改后的答案
     */
    public int updateAnswer(Answer answer);

    /*
    从数据库中修改指定id的答案
    输入：答案所对应的题目id
    输入：查找到的对应的Answer对象
     */
    public Answer findAnswerById(Integer question_id);

    public List<Answer> findAllAnswer();
}
