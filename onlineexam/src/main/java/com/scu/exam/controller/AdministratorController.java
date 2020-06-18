package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.service.AdministratorService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@Api(tags = "管理员模块")
@Controller
@RequestMapping("admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @ApiOperation("添加管理员个人信息，注册时用")
    @PostMapping("/addAdmin")
    public void addAdmin(@RequestBody JSONObject adminInfo, HttpServletResponse response){
        System.out.println(adminInfo.toString());
        Administrator administrator = new Administrator();
        administrator.setAd_name((String)adminInfo.get("ad_name"));
        administrator.setAd_password((String)adminInfo.get("ad_password"));
        administrator.setAd_image((String)adminInfo.get("ad_image"));

        

        try{
            administratorService.insertAdministrator(administrator);
        }catch (DataIntegrityViolationException e){
            System.out.println("整入数据违反数据库完整性");
        }

        ResponseUtils.renderJson(response, "注册成功！！！！");
    }

    @ApiOperation("获取管理员个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ad_id", dataType = "String", required = true, value = "当前管理员Id")
    })
    @GetMapping("/getAdmin")
    public void getAdminInfo(String ad_id, HttpServletResponse response){
        Administrator administrator = administratorService.findAdministratorById(ad_id);
        if (administrator != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("admin", administrator);
            ResponseUtils.renderJson(response, jsonObject);
        }

        //测试用，待删
        System.out.println(response);
    }


}
