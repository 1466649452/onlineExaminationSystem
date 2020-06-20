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

    @ApiOperation("获取教师信息(测试通过)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "String", required = true, value = "教师id")
    })
    @GetMapping("/getTeacherInfo")
    public void getTeacherInfo(String t_id, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        try {
            Teacher teacher = (Teacher) teacherService.findTeacherById(t_id);
            if (teacher == null) {
                res.put("status", "fail");
                res.put("error", "没有教师信息");
            } else {
                res.put("status", "success");
                JSONObject tt = (JSONObject) JSONObject.toJSON(teacher);
                res.put("data", tt);
            }
            ResponseUtils.renderJson(response, res);
        } catch (Exception e) {
            res.put("status", "fail");
            ResponseUtils.renderJson(response, res);
        }
    }

    @ApiOperation("获取同名教师信息(测试通过）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_name", dataType = "String", required = true, value = "教师姓名")
    })
    @GetMapping("/getNameTeacherInfo")
    public void getNameTeacherInfo(String t_name, HttpServletResponse response) {
        JSONObject js = new JSONObject();
        try {
            List<Teacher> teacher = teacherService.findTeacherByname(t_name);
            if (teacher == null || teacher.size() == 0) {
                js.put("error", "没有教师信息");
                js.put("status", "fail");
            } else {
                js.put("status", "success");
                JSONArray json = (JSONArray) JSONArray.toJSON(teacher);
                js.put("data", json);
            }
            ResponseUtils.renderJson(response, js);
        } catch (Exception e) {
            js.put("status", "fail");
            ResponseUtils.renderJson(response, js);
        }

    }

    @ApiOperation("添加教师信息(测试通过）")
    @PostMapping("/insertOneTeacher")
    public void insertOneTeacher(@RequestBody JSONObject data, HttpServletResponse response) {
        JSONObject responsetoforward=new JSONObject();

        try{
            String t_id = data.get("t_id") != null ? data.get("t_id").toString() : null;
            String t_name = data.get("t_name") != null ? data.get("t_name").toString() : null;
            String t_password = data.get("t_password") != null ? data.get("t_password").toString() : null;
            String t_image = data.get("t_image") != null ? data.get("t_image").toString() : null;

            //必要数据校验 t_id
            if (t_id == null) {
                responsetoforward.put("status", "fail");
            }else{
                Teacher tea = new Teacher(t_id, t_name, t_password, t_image);
                if (teacherService.insertOneTeacher(tea) != 0) {
                    responsetoforward.put("status", "success");
                } else {
                    responsetoforward.put("status", "fail");
                }
            }
            ResponseUtils.renderJson(response, responsetoforward);
        }catch (Exception e){
            responsetoforward.put("status", "fail");
            ResponseUtils.renderJson(response, responsetoforward);
        }



    }

    @ApiOperation("修改教师信息(测试通过）")
    @PostMapping("/updateOneTeacher")
    public void updateOneTeacher(@RequestBody JSONObject data, HttpServletResponse response) {
        //必要数据校验 stu_id,paper_id
        String t_id = (String) data.get("t_id");
        JSONObject res = new JSONObject();
        if (data.get("t_id") == null) {
            res.put("status", "fail");
            res.put("error", "没有传入必要信息");
            ResponseUtils.renderJson(response, res);
            return;
        }

        if (data.get("t_password") != null) {
            String password = (String) data.get("t_password");
            if (teacherService.updateTeacherPassword(t_id, password) != 0) {
                res.put("status", "success");
            } else {
                res.put("status", "fail");
            }
        }
        if (data.get("t_name") != null) {
            String t_name = (String) data.get("t_name");
            if (teacherService.updateTeacherName(t_id, t_name) != 0) {
                res.put("status", "success");
            } else {
                res.put("status", "fail");
            }
        }
        if (data.get("t_image") != null) {
            String t_image = (String) data.get("t_image");
            if (teacherService.updateTeacherHeadimage(t_id, t_image) != 0) {
                res.put("status", "success");
            } else {
                res.put("status", "fail");
            }
        }
        ResponseUtils.renderJson(response, res);
    }

    @ApiOperation("删除教师信息(测试通过）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "t_id", dataType = "Integer", required = true, value = "教师id")
    })
    @PostMapping("/deleteOneTeacher")
    public void deleteOneScore(@RequestBody JSONObject data, HttpServletResponse response) {
        int t = teacherService.deleteTeacherByid(data.get("t_id").toString());
        JSONObject js = new JSONObject();
        if (t != 0) {
            js.put("status", "success");
        } else {
            js.put("status", "fail");
        }
        ResponseUtils.renderJson(response, js);
    }

}
