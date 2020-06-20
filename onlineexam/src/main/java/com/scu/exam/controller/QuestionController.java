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
        System.out.println("传入的JSONObject中的数据："+jsonObject.toString());

        //将jsonObject中的数据取出放到question中
        Question question = setQuestionByJSON(jsonObject);
        JSONObject jsonObject1 = new JSONObject();

        //查找数据库中是否有相同题干的题目，来判断该题目是否已经存在
        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            //该题目已经存在
            jsonObject1.put("status", "失败！该题目已存在！");
        }else{
            try{
                //将question插入到question表中
                questionService.insertQuestion(question);
                /* 同时也要把题目设置的选项添加到数据库的answer表中 */
                Integer question_id = questionService.findQuestionByInfo(question.getQuestion_info()).getQuestion_id();
                answerController.addAnswer(question_id, jsonObject);
                jsonObject1.put("status", "添加成功！");
            }catch (DataAccessException e){
                jsonObject1.put("status", "添加失败！");
            }
        }
        ResponseUtils.renderJson(response, jsonObject1);

        System.out.println("传到前端的response："+response);
    }

    @ApiOperation("修改题目")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateQuestion")
    public void updateQuestion(@RequestBody JSONObject jsonObject, HttpServletResponse response){
        System.out.println("传入的JSONObject中的数据："+jsonObject.toString());
        JSONObject jsonObject1 = new JSONObject();

        //将前端传来的jsonObject中的值取出放到一个Question实例中
        Question question = setQuestionByJSON(jsonObject);
        question.setQuestion_id(jsonObject.getInteger("question_id"));

        //根据修改后的题干信息在数据库中查找是否有相同的题目
        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            //修改后的题目在数据库中已存在
            jsonObject1.put("status", "失败！该题目已存在！");
        }else{
            try{
                /* 在数据库中修改题目的信息 */
                questionService.updateQuestion(question);
                /* 同时也要在数据库中修改各个选项 */
                answerController.updateAnswer(jsonObject);
                jsonObject1.put("status", "修改成功！");
            }catch (DataAccessException e){
                jsonObject1.put("status", "修改失败！");
            }
        }
        ResponseUtils.renderJson(response, jsonObject1);

        System.out.println("传到前端的response："+response);
    }

    @ApiOperation("通过试题的id查找题目")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "question_id", dataType = "Integer", required = true, value = "要查找的题目的id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getQuestionById")
    public void getQuestionById(Integer question_id, HttpServletResponse response){
        System.out.println("传入的question_id："+question_id);
        JSONObject resultJson = new JSONObject();
        try {
            //通过question_id在数据库中查找question
            Question question = questionService.findQuestionById(question_id);
            if (question != null){
                //将查找结果放入到resultJson中
                resultJson.put("question_id", question.getQuestion_id());
                resultJson.put("question_info", question.getQuestion_info());
                resultJson.put("correct_info", question.getCorrect_answer());
                resultJson.put("type", question.getType());
                resultJson.put("answerList", answerController.getAnswerById(question.getQuestion_id()));
                resultJson.put("status", "查找成功");
            }else{
                //如果question==null，则表明要查找的id在题库中不存在
                resultJson.put("status", "查找失败！该题目不存在！");
            }
        } catch (DataAccessException e){
            //对数据库操作发生的异常
            resultJson.put("status", "查找失败！");
        }
        ResponseUtils.renderJson(response, resultJson);

        System.out.println("传到前端的response："+response);
    }

    @ApiOperation("通过题干的关键字查找题目")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "keyword", dataType = "String", required = true, value = "题干中的关键词")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getQuestionByKeyword")
    public void getQuestionByKeyword(String keyword, HttpServletResponse response){
        System.out.println("要查找的关键词为："+keyword);

        JSONObject resultJSON = new JSONObject();
        try {
            //通过关键字查找得到一个question列表
            List<Question> questionList = questionService.findQuestionByKeyword(keyword);

            List<JSONObject> jsonObjectList = null;
            for (int i = 0; i < questionList.size(); i++){
            /*
               将题目及其答案列表都放入一个JSONObject中
               quesWithAns的结构如下：
                   {
                     “question_id": question_id
                     "question_info": question_info
                     "correct_answer": correct_answer
                     "type": type
                     "answerList": {
                            {"answer": answer}
                            {"answer": answer}
                            ..................
                            {"answer": answer}
                     }
                   }
             */
                JSONObject quesWithAns = new JSONObject();
                Question question = questionList.get(i);
                //将查找结果put到quesWithAns中
                quesWithAns.put("question_id", question.getQuestion_id());
                quesWithAns.put("question_info", question.getQuestion_info());
                quesWithAns.put("correct_answer", question.getCorrect_answer());
                quesWithAns.put("type", question.getType());
                //查找题目对应的答案选项
                quesWithAns.put("answerList", answerController.getAnswerById(question.getQuestion_id()));
                //将查找到的选项列表放入到quesWithAns中
                jsonObjectList.add(quesWithAns);
            }
            resultJSON.put("questionList", jsonObjectList);
            resultJSON.put("status", "查找成功！");
        }catch (DataAccessException e){
            resultJSON.put("questionList", null);
            resultJSON.put("status", "查找失败！");
        }
        //将查找结果放入到response中
        ResponseUtils.renderJson(response, resultJSON);

        System.out.println(response);
    }

    @ApiOperation("删除指定id的试题")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "question_id", dataType = "Integer", required = true, value = "题目的Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/deleteQuestion")
    public void deleteQuestion(Integer question_id, HttpServletResponse response){
        System.out.println("要删除的题目的Id:"+question_id);

        JSONObject jsonObject = new JSONObject();
        try {
            //在数据库中的question表中删除相应的题目
            questionService.deleteQuestionById(question_id);
            // 同时也要在数据库的answer中删除各个选项
            answerController.deleteAnswerById(question_id);
            jsonObject.put("status", "删除成功！");
        }catch (DataAccessException e){
            jsonObject.put("status", "删除失败！");
        }
        ResponseUtils.renderJson(response, jsonObject);

        System.out.println("传到前端的response："+response);
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
