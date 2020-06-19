package com.scu.exam.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Score;
import com.scu.exam.pojo.Teacher;
import com.scu.exam.service.TeacherService;
import com.scu.exam.utils.JsonOperation;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "教师信息相关API")
@Controller
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("获取教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "String", required = true, value = "教师id")
    })
    @GetMapping("/getTeacherInfo")
    public void getTeacherInfo(String t_id, HttpServletResponse response){
        Teacher teacher=(Teacher)teacherService.findTeacherById(t_id);
        JSONObject js=new JSONObject();
        if(teacher==null){
            js.put("status","success");
            js.put("error","没有教师信息");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","success");
            JSONObject tt=(JSONObject) JSONObject.toJSON(teacher);
            JsonOperation.combineJson(js,tt);
            ResponseUtils.renderJson(response,js);
        }
    }

    @ApiOperation("获取同名教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_name", dataType = "String", required = true, value = "教师姓名")
    })
    @GetMapping("/getNameTeacherInfo")
    public void getNameTeacherInfo(String t_name, HttpServletResponse response){
        List<Teacher> teacher=teacherService.findTeacherByname(t_name);

        if(teacher==null||teacher.size()==0){
            JSONObject js=new JSONObject();
            js.put("status","success");
            js.put("error","没有教师信息");
            ResponseUtils.renderJson(response,js);
        }else{
            JSONArray json=(JSONArray)JSONArray.toJSON(teacher);
            ResponseUtils.renderJson(response,json);
        }
    }

    @ApiOperation("添加教师信息")
    @PostMapping("/insertOneTeacher")
    public void insertOneTeacher(@RequestBody JSONObject data,HttpServletResponse response){
        JSONObject js=(JSONObject) JSONObject.toJSON(data.get("teacher"));
        //必要数据校验 t_id
        if(js.get("t_id")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
            return;
        }
        Teacher tea=new Teacher((String) js.get("t_id"),(String)js.get("t_name"),(String)js.get("t_password"),(String)js.get("t_image"));
        if(teacherService.insertOneTeacher(tea)!=0){
            JSONObject res=new JSONObject();
            res.put("status","success");
            ResponseUtils.renderJson(response,res);
        }else{
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","未知错误");
            ResponseUtils.renderJson(response,res);
        }


    }

    @ApiOperation("修改教师信息")
    @PostMapping("/updateOneTeacher")
    public void updateOneTeacher(@RequestBody JSONObject data, HttpServletResponse response){
        //必要数据校验 stu_id,paper_id
        String t_id=(String)data.get("t_id");
        if(data.get("t_id")==null){
            JSONObject res=new JSONObject();
            res.put("status","fail");
            res.put("error","没有传入必要信息");
            ResponseUtils.renderJson(response,res);
            return;
        }
        if(data.get("t_password")!=null){
            String password=(String)data.get("t_password");
            teacherService.updateTeacherPassword(t_id,password);
        }
        if(data.get("t_name")!=null){
            String t_name=(String)data.get("t_name");
            teacherService.updateTeacherName(t_id,t_name);
        }
        if(data.get("t_image")!=null){
            String t_image=(String)data.get("t_image");
            teacherService.updateTeacherHeadimage(t_id,t_image);
        }

    }

    @ApiOperation("删除教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "Integer", required = true, value = "教师id")
    })
    @PostMapping("/deleteOneScore")
    public void deleteOneScore(String t_id, HttpServletResponse response){
        int t=teacherService.deleteTeacherByid(t_id);
        JSONObject js=new JSONObject();
        if(t!=0){
            js.put("status","success");
            ResponseUtils.renderJson(response,js);
        }else{
            js.put("status","fail");
            ResponseUtils.renderJson(response,js);
        }
    }

}
