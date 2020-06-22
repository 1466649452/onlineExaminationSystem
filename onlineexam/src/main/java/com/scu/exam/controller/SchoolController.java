package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.pojo.Answer;
import com.scu.exam.pojo.School;
import com.scu.exam.service.AdministratorService;
import com.scu.exam.service.AnswerService;
import com.scu.exam.service.SchoolService;
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
import java.util.Iterator;

@Api(tags = "学校信息相关API")
@Controller
@RequestMapping("school")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    //添加学校信息
    @ApiOperation("添加学校信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addSchool")
    public void addAdmin(@RequestBody JSONObject schoolInfo, HttpServletResponse response){
        System.out.println(schoolInfo.toString());

        School school = setSchoolByJSON(schoolInfo);
        School school1 = schoolService.findSchoolByName(school.getSchool_name());

        if(school1.getSchool_name().equals(school.getSchool_name())){
            ResponseUtils.renderJson(response, "失败！该学校已存在！");
        }else{
            try{
                schoolService.insertSchool(school);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "注册失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "注册成功！！！！");

        System.out.println(response);
    }

    //查看学校信息
    @ApiOperation("查看学校信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "school_name", dataType = "String", required = true, value = "学校名称")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getSchool")
    public void getSchoolInfo(String school_name, HttpServletResponse response){
        School school = schoolService.findSchoolByName(school_name);
        if (school != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("school", school);
            ResponseUtils.renderJson(response, jsonObject);
        }

        //测试用，待删
        System.out.println(response);
    }

    //修改学校信息
    @ApiOperation("修改学校信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "school_name", dataType = "String", required = true, value = "学校名称")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateSchool")
    public void updateAdminInfo(String school_name, @RequestBody JSONObject schoolInfo, HttpServletResponse response){
        School school = setSchoolByJSON(schoolInfo);
        school.setSchool_name(school_name);

        /* 在数据库修改相对应的学校信息 */
        try{
            schoolService.updateSchool(school);
        }catch (DataAccessException e){
            ResponseUtils.renderJson(response, "修改失败！");
        }
        ResponseUtils.renderJson(response, "修改成功！");

        System.out.println(response);
    }

    //删除学校信息
    @ApiOperation("删除学校信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "school_name", dataType = "String", required = true, value = "学校名称")
    })
    @PostMapping("/deleteSchool")
    public void deleteOneScore(String school_name, HttpServletResponse response){
        int s=schoolService.deleteSchoolByName(school_name);
        JSONObject js=new JSONObject();
        if(s!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }



    /*
       将前端传回的json对象中信息取出存入school对象中
    */
    public School setSchoolByJSON(JSONObject jsonObject){
        School school= new School();
        school.setSchool_name((String)jsonObject.get("school_name"));
        school.setSchool_address((String)jsonObject.get("school_address"));
        school.setSchool_phone((String)jsonObject.get("school_phone"));
        return school;
    }

}
