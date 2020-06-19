package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    /*
    在添加试题时，也要添加该试题的各个选项
     */
    public void addAnswer(@RequestBody JSONObject jsonObject){
        
    }

    public void updateAnswer(@RequestBody JSONObject jsonObject){

    }

    public void deleteAnswerById(Integer question_id){

    }

    public void getAnswerById(@RequestBody JSONObject jsonObject){

    }
}
