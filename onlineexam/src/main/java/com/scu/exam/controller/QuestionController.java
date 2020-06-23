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
import java.util.ArrayList;
import java.util.List;

@Api(tags = "试题管理模块")
@Controller
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerController answerController;

    @ApiOperation("得到所有的试题列表")
    /*
       响应的数据结构：是由quesWithAns构成的列表，再将该列表放入到一个JSONObject中

               单个quesWithAns的结构如下：
                   {
                     “question_id": question_id,
                     "question_info": question_info,
                     "correct_answer": correct_answer,
                     "type": type,
                     "answerList": [
                            "answer1",
                            "answer"2,
                            ........,
                            "answern"
                      ]
                      "status": status
                   }
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/findAllQuestion")
    public void findAllQuestion(HttpServletResponse response){
        JSONObject resultJSON = new JSONObject();
        List<JSONObject> jsonObjectList = new ArrayList<>();
        try {
            //得到所有的Question列表
            List<Question> questionList = questionService.findAllQuestion();
            for (int i = 0; i < questionList.size(); i++){
                //将每一个quesion及其answer放入到一个JSONObject中
                JSONObject quesWithAns = new JSONObject();
                Question question = (Question)questionList.get(i);
                quesWithAns.put("question_id", question.getQuestion_id());
                quesWithAns.put("question_info", question.getQuestion_info());
                quesWithAns.put("correct_answer", question.getCorrect_answer());
                quesWithAns.put("type", question.getType());
                quesWithAns.put("answerList", answerController.getAnswerById(question.getQuestion_id()));

                //将得到的JSONObject放入到一个List中
                jsonObjectList.add(quesWithAns);
            }
            resultJSON.put("questionList", jsonObjectList);
            resultJSON.put("status", "查找成功！");
        }catch (DataAccessException e){
            resultJSON.put("status", "查找失败！"+"\\n"+"详情："+e.getMessage());
        }
        //封装结果
        ResponseUtils.renderJson(response, resultJSON);
    }

    @ApiOperation("添加题目到数据库中（测试通过）")
    /*
       参数JSONObject data的格式：
          {
           "question_info": question_info,
           "correct_answer": correct_answer,
           "type": type,
           "answerList": [ "answer1",
                           "answer2",
                           .........,
                           "answern"
                         ]
          }
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addQuestion")
    public void addQuestion(@RequestBody JSONObject data, HttpServletResponse response){
        //将jsonObject中的数据取出放到question中
        Question question = setQuestionByJSON(data);
        JSONObject jsonObject = new JSONObject();

        //查找数据库中是否有相同题干的题目，来判断该题目是否已经存在
        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            //该题目已经存在
            jsonObject.put("status", "失败！该题目已存在！");
        }else{
            try{
                //将question插入到question表中
                questionService.insertQuestion(question);
                //得到系统对所插入题目生成的id,用来设置要插入的answer
                Integer question_id = questionService.findQuestionByInfo(question.getQuestion_info()).getQuestion_id();
                //同时也要把题目设置的选项添加到数据库的answer表中
                System.out.println(data);
                answerController.addAnswer(question_id, data);
                //反馈结果
                jsonObject.put("status", "添加成功！");
            }catch (DataAccessException e){
                //反馈异常
                jsonObject.put("status", "添加失败！数据库操作失误！"+"\n"+"详情："+e.getMessage());
            }
        }
        //将结果jsonObject1存入到response中，反馈给前端
        ResponseUtils.renderJson(response, jsonObject);
    }

    @ApiOperation("修改题目（测试通过）")
    /*
       参数JSONObject data的格式：
          {
           "question_id": question_id,
           "question_info": question_info,
           "correct_answer": correct_answer,
           "type": type,
           "answerList": [ "answer1",
                           "answer2",
                           .........,
                           "answern"
                         ]
          }
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateQuestion")
    public void updateQuestion(@RequestBody JSONObject data, HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();

        //将前端传来的jsonObject中的值取出放到一个Question实例中
        Question question = setQuestionByJSON(data);
        question.setQuestion_id(data.getInteger("question_id"));

        //根据修改后的题干信息在数据库中查找是否有相同的题目
        Question questionSql = questionService.findQuestionByInfo(question.getQuestion_info());
        if(questionSql != null){
            //修改后的题目在数据库中已存在
            jsonObject.put("status", "失败！修改后的题目已存在！");
        }else{
            try{
                /* 在数据库中修改题目的信息 */
                questionService.updateQuestion(question);
                /* 同时也要在数据库中修改各个选项answer */
                answerController.updateAnswer(data);
                jsonObject.put("status", "修改成功！");
            }catch (DataAccessException e){
                jsonObject.put("status", "修改失败！数据库操作失误！"+"\n"+"详情："+e.getMessage());
            }
        }
        ResponseUtils.renderJson(response, jsonObject);
    }

    @ApiOperation("通过试题的id查找题目(测试通过）")
    /*
      输入的数据data:
        {
          "question_id": question_id
        }
      响应的查找结果：
        {
          "question_id": question_id,
          "question_info": question_info,
          "correct_answer": correct_answer,
          "type": type,
          "answerList":
              [
                "answer1",
                "answer2",
                .........,
                "answern"
              ]
        }
     */

    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/getQuestionById")
    public void getQuestionById(@RequestBody JSONObject data, HttpServletResponse response){
        Integer question_id = data.getInteger("question_id");
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
            resultJson.put("status", "查找失败！数据库操作失误！"+"\n"+"详情："+e.getMessage());
        }
        ResponseUtils.renderJson(response, resultJson);
    }

    @ApiOperation("通过题干的关键字查找题目（测试通过）")
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
        JSONObject resultJSON = new JSONObject();
        try {
            //通过关键字查找得到一个question列表
            List<Question> questionList = questionService.findQuestionByKeyword(keyword);

            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (int i = 0; i < questionList.size(); i++){
            /*
               将题目及其答案列表都放入一个JSONObject中
               quesWithAns的结构如下：
                   {
                     “question_id": question_id,
                     "question_info": question_info,
                     "correct_answer": correct_answer,
                     "type": type,
                     "answerList": [
                            "answer1",
                            "answer"2,
                            ........,
                            "answern"
                      ]
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
    }

    @ApiOperation("删除指定id的试题（测试通过）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/deleteQuestion")
    public void deleteQuestion(@RequestBody JSONObject data, HttpServletResponse response){
        Integer question_id = data.getInteger("question_id");

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
