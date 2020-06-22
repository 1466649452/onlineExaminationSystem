package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
import com.scu.exam.service.AnswerService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    /*
    在添加试题时，也要添加该试题的各个选项
     */
    public void addAnswer(Integer question_id, JSONObject data){
        //得到data中的存有answer选项的列表的Iterator
        Iterator<Object> answerList = data.getJSONArray("answerList").iterator();
        while ((answerList.hasNext())){
            //得到当前answer的内容
            String answerContent = (String)answerList.next();
            //初始化一个answer实例
            Answer answer = new Answer(question_id, answerContent);
            //向数据库中插入answer实例
            answerService.insertAnswer(answer);
        }
    }

    public void updateAnswer(JSONObject data){
        //得到要操作的answer的ID
        Integer question_id = data.getInteger("question_id");
        //由于不能批量修改，所以此处先删除，再添加，使修改更具有灵活性
        answerService.deleteAnswerById(question_id);
        //添加修改后的answer选项
        addAnswer(question_id, data);
    }

    public void deleteAnswerById(Integer question_id){
        answerService.deleteAnswerById(question_id);
    }

    public List<String> getAnswerById(Integer question_id){
        List<String> answerList = new ArrayList<>();
        List<Answer> answers = answerService.findAnswerById(question_id);
        for (int i = 0; i < answers.size(); i++){
            answerList.add(answers.get(i).getAnswer());
        }
        return answerList;
    }
}
