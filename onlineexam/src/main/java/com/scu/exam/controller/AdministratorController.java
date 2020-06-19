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
        System.out.println("传入的JSONObject中的数据："+adminInfo.toString());
        //将前端传回的adminInfo中的信息提取到一个Administrator实例中
        Administrator administrator = setAdminByJSON(adminInfo);

        //查找数据库中与该管理员同名(ad_name)的其他管理员
        Administrator adminSql = administratorService.findAdminByName(administrator.getAd_name());
        //同名的管理员存在，则不能添加管理员
        if(adminSql != null){
            ResponseUtils.renderJson(response, "失败！该用户已存在！");
        }else{
            try{
                //无同名管理员，则在数据库中添加该管理员姓名
                administratorService.insertAdministrator(administrator);
            }catch (DataAccessException e){
                //捕捉数据库插入异常
                ResponseUtils.renderJson(response, "注册失败！！！");
            }
            ResponseUtils.renderJson(response, "注册成功！！！！");
        }

        //待删
        System.out.println("传到前端的response："+response.toString());
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
        System.out.println("要查找的ad_id:"+ad_id);
        JSONObject jsonObject = new JSONObject();
        try {
            //在数据库中查找Administrator
            Administrator administrator = administratorService.findAdministratorById(ad_id);
            if (administrator != null){
                //如果不为空，则表示查找到数据
                jsonObject.put("admin", administrator);
                jsonObject.put("status", "查找成功！");
            }else {
                //查询结果为空，则表明该用户不存在
                jsonObject.put("admin", null);
                jsonObject.put("status", "该用户不存在！");
            }
        }catch (DataAccessException e){
            jsonObject.put("admin", null);
            jsonObject.put("status", "对数据库的操作失败！！");
        }
        //查询结果放入到response中，返回给前端
        ResponseUtils.renderJson(response, jsonObject);

        //测试用，待删
        System.out.println("传到前端的response："+response.toString());
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

         JSONObject jsonObject = new JSONObject();
         //判断该用户在数据库中是否存在
         if (administratorService.findAdministratorById(ad_id) != null){
             //判断修改后的用户在数据库中是否已存在
             if (administratorService.findAdminByName(administrator.getAd_name()) == null){
                 try{
                     /* 在数据库修改相对应的管理员信息 */
                     administratorService.updateAdministrator(administrator);
                     jsonObject.put("status", "修改成功！");
                 }catch (DataAccessException e){
                     jsonObject.put("status", "修改失败！对数据库的操作失误！");
                 }
             }else {
                 jsonObject.put("status", "该用户名已被使用，请重新修改！");
             }
         }else {
             jsonObject.put("status", "该用户不存在！");
         }
         ResponseUtils.renderJson(response, jsonObject);

        //测试用，待删
        System.out.println("传到前端的response："+response.toString());
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
