package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Question;
import com.scu.exam.service.QuestionService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Api(tags = "试题管理模块")
@Controller
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;


    @ApiOperation("添加题目到数据库中")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addQuestion")
    public void addQuestion(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        System.out.println(jsonObject.toString());

        Question question = setQuestionByJSON(jsonObject);

        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            ResponseUtils.renderJson(response, "失败！该题目已存在！");
        }else{
            try{
                questionService.insertQuestion(question);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "添加失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "添加成功！！！！");

        System.out.println(response);
    }

    @ApiOperation("修改question")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateQuestion")
    public void updateQuestion(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        Question question = setQuestionByJSON(jsonObject);
        question.setQuestion_id((Integer)jsonObject.get("question_id"));

        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            ResponseUtils.renderJson(response, "失败！该题目已存在！");
        }else{
            try{
                /* 在数据库中修改题目的信息 */
                questionService.updateQuestion(question);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "修改失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "修改成功！");

        System.out.println(response);
    }

    @ApiOperation("通过试题的id查找题目")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getQuestionById")
    public void getQuestionById(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        Question question = questionService.findQuestionById((Integer)jsonObject.get("question_id"));
        if (question != null){
            JSONObject resultJson = new JSONObject();
            resultJson.put("question", question);
            ResponseUtils.renderJson(response, resultJson);
        }else{
            ResponseUtils.renderJson(response, "查找失败！该题目不存在！");
        }
    }

    /*
       将前端传回的json对象中信息取出
       (包括question_info, correct_answer, type)，
       放入Question实例中
    */
    public Question setQuestionByJSON(JSONObject jsonObject){
        Question question = new Question();
        question.setQuestion_info((String)jsonObject.get("question_info"));
        question.setCorrect_answer((String)jsonObject.get("correct_answer"));
        question.setType((String)jsonObject.get("type"));
        return question;
    }

}
