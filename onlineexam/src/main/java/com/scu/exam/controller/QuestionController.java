package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Answer;
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
import java.sql.SQLException;
import java.util.List;

@Api(tags = "试题管理模块")
@Controller
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    private AnswerController answerController;


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
                /* 同时也要吧题目设置的选项添加到数据库的answer表中 */
                answerController.addAnswer(jsonObject);
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
                /* 同时也要在数据库中修改各个选项 */
                answerController.updateAnswer(jsonObject);
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
            resultJson.put("answerList", answerController.getAnswerById(question.getQuestion_id()));
            ResponseUtils.renderJson(response, resultJson);
        }else{
            ResponseUtils.renderJson(response, "查找失败！该题目不存在！");
        }
    }

    @ApiOperation("通过题干的关键字查找题目")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getQuestionByKeyword")
    public void getQuestionByKeyword(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        String keyword = (String)jsonObject.get("keyword");
        List<Question> questionList = questionService.findQuestionByKeyword(keyword);

        List<JSONObject> jsonObjectList = null;
        for (int i = 0; i < questionList.size(); i++){
            JSONObject quesWithAns = new JSONObject();
            Question question = questionList.get(i);
            quesWithAns.put("question", question);
            quesWithAns.put("answerList", answerController.getAnswerById(question.getQuestion_id()));
            jsonObjectList.add(quesWithAns);
        }

        JSONObject resultJSON = new JSONObject();
        resultJSON.put("questionList", jsonObjectList);
        ResponseUtils.renderJson(response, resultJSON);
        System.out.println(response);
    }

    @ApiOperation("删除指定id的试题")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/deleteQuestion")
    public void deleteQuestion(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        Integer question_id = (Integer)jsonObject.get("question_id");
        try {
            questionService.deleteQuestionById(question_id);
            /* 同时也要在数据库中删除各个选项 */
            answerController.deleteAnswerById(question_id);
        }catch (DataAccessException e){
            ResponseUtils.renderJson(response, "删除失败！");
        }
        ResponseUtils.renderJson(response, "删除成功！");

        System.out.println(response);
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
