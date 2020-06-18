package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.service.AdministratorService;
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

@Api(tags = "管理员模块")
@Controller
@RequestMapping("admin")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @ApiOperation("添加管理员个人信息，注册时用")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/addAdmin")
    public void addAdmin(@RequestBody JSONObject adminInfo, HttpServletResponse response){
        System.out.println(adminInfo.toString());

        Administrator administrator = setAdminByJSON(adminInfo);

        Administrator adminSql = administratorService.findAdminByName(administrator.getAd_name());
        if(adminSql.getAd_password().equals(administrator.getAd_password())){
            ResponseUtils.renderJson(response, "失败！该用户已存在！");
        }else{
            try{
                administratorService.insertAdministrator(administrator);
            }catch (DataAccessException e){
                ResponseUtils.renderJson(response, "注册失败！！！");
            }
        }
        ResponseUtils.renderJson(response, "注册成功！！！！");

        System.out.println(response);
    }

    @ApiOperation("管理员查看本人的个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ad_id", dataType = "String", required = true, value = "当前管理员Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
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


    @ApiOperation("管理员修改自己的个人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ad_id", dataType = "String", required = true, value = "当前管理员Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    @PostMapping("/updateAdmin")
    public void updateAdminInfo(String ad_id, @RequestBody JSONObject adminInfo, HttpServletResponse response){
         Administrator administrator = setAdminByJSON(adminInfo);
         administrator.setAd_id(ad_id);

         /* 在数据库修改相对应的管理员信息 */
         try{
             administratorService.updateAdministrator(administrator);
         }catch (DataAccessException e){
             ResponseUtils.renderJson(response, "修改失败！");
         }
         ResponseUtils.renderJson(response, "修改成功！");

         System.out.println(response);
    }


    /*
       将前端传回的json对象中信息取出
       (包括ad_name, ad_password, ad_image)，
       放入Administrator实例中
    */
    public Administrator setAdminByJSON(JSONObject jsonObject){
        Administrator administrator = new Administrator();
        administrator.setAd_name((String)jsonObject.get("ad_name"));
        administrator.setAd_password((String)jsonObject.get("ad_password"));
        administrator.setAd_image((String)jsonObject.get("ad_image"));
        return administrator;
    }

}
