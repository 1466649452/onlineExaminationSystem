package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Classes;
import com.scu.exam.service.ClassesService;
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

@Api(tags = "班级管理相关API")
@Controller
@RequestMapping("classes")
public class ClassesController {
    @Autowired
    private ClassesService classesService;

    //添加班级信息
    @ApiOperation("添加班级信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addClass")
    public void addClass(@RequestBody JSONObject classInfo, HttpServletResponse response){
        System.out.println(classInfo.toString());

        Classes classes = setClassByJSON(classInfo);
        Classes classes1 = classesService.findClassById(classes.getClass_id());

        if(classes1.getClass_id().equals(classes1.getClass_id())){
            ResponseUtils.renderJson(response, "失败！该班级已存在！");
        }else{
            try{
                classesService.insertClass(classes);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "注册失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "注册成功！！！！");

        System.out.println(response);
    }

    //查看班级信息
    @ApiOperation("查看班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "class_id", dataType = "String", required = true, value = "班级id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @GetMapping("/getClass")
    public void getClassInfo(String class_id, HttpServletResponse response){
        Classes classes = classesService.findClassById(class_id);
        if (classes != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("classes", classes);
            ResponseUtils.renderJson(response, jsonObject);
        }

        //测试用，待删
        System.out.println(response);
    }

    //修改班级信息
    @ApiOperation("修改班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "class_id", dataType = "String", required = true, value = "班级id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateClass")
    public void updateClass(String class_id, @RequestBody JSONObject classInfo, HttpServletResponse response){
        Classes classes = setClassByJSON(classInfo);
        classes.setClass_id(class_id);

        /* 在数据库修改相对应的班级信息 */
        try{
            classesService.updateClass(classes);
        }catch (DataAccessException e){
            ResponseUtils.renderJson(response, "修改失败！");
        }
        ResponseUtils.renderJson(response, "修改成功！");

        System.out.println(response);
    }

    //删除班级信息
    @ApiOperation("删除班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "class_id", dataType = "String", required = true, value = "班级id")
    })
    @PostMapping("/deleteClass")
    public void deleteClass(String class_id, HttpServletResponse response){
        int c = classesService.deleteClassById(class_id);
        JSONObject js=new JSONObject();
        if(c!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }



    /*
       将前端传回的json对象中信息取出
       (包括ad_name, ad_password, ad_image)，
       放入Administrator实例中
    */
    public Classes setClassByJSON(JSONObject jsonObject){
        Classes classes = new Classes();
        classes.setStu_number((Integer) jsonObject.get("stu_number"));
        classes.setSchool((String)jsonObject.get("school"));
        classes.setT_id((String)jsonObject.get("t_id"));
        classes.setClass_id((String)jsonObject.get("class_id"));
        classes.setGrade((Integer) jsonObject.get("grade"));
        return classes;
    }

}

