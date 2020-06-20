package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
import com.scu.exam.service.AnswerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Iterator;
import java.util.List;

@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    /*
    在添加试题时，也要添加该试题的各个选项
     */
    public void addAnswer(Integer question_id, JSONObject jsonObject){
        Iterator<Object> answerList = jsonObject.getJSONArray("answeraList").iterator();
        while ((answerList.hasNext())){
            Answer answer = getAnswerByJSON((JSONObject)answerList.next(), question_id);
            answerService.insertAnswer(answer);
        }
    }

    public void updateAnswer(JSONObject jsonObject){
        Iterator<Object> answerList = jsonObject.getJSONArray("answerList").iterator();
        while ((answerList.hasNext())){
            Answer answer = getAnswerByJSON((JSONObject)answerList.next(), jsonObject.getInteger("question_id"));
            answerService.updateAnswer(answer);
        }
    }

    public void deleteAnswerById(Integer question_id){
        answerService.deleteAnswerById(question_id);
    }

    public List<Answer> getAnswerById(Integer question_id){
        return answerService.findAnswerById(question_id);
    }

    public Answer getAnswerByJSON(JSONObject jsonObject, Integer question_id){
        Answer answer = new Answer();
        String answerContent = jsonObject.getString("answer");

        answer.setQuestion_id(question_id);
        answer.setAnswer(answerContent);
        return  answer;
    }
}
