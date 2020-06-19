package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Administrator;
import com.scu.exam.service.AdministratorService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class AdministratorControllerTest {
    @Autowired
    private AdministratorController administratorController;
    @Autowired
    private AdministratorService administratorService;

    @Before
    public void setUp() throws Exception {
        Administrator administrator1 = new Administrator("aaaaaa", "ssssss", "dddddd", "ffffff");
        administratorService.insertAdministrator(administrator1);
        Administrator administrator2 = new Administrator("tttttt", "uuuuuu", "cccccc", "xxxxxx");
        administratorService.insertAdministrator(administrator2);
    }

    @After
    public void tearDown() throws Exception {
        List<Administrator> administratorList = administratorService.findAllAdministrator();
        System.out.println("运行后数据库中的数据：");
        for (int i = 0; i < administratorList.size(); i++){
            Administrator administrator = administratorList.get(i);
            System.out.println(administrator.toString());
            administratorService.deleteAdministrator(administrator);
        }
    }

    //初始化创建一个JSONObject
    public JSONObject initJSONObject(String ad_name, String ad_password, String ad_image){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ad_name", ad_name);
        jsonObject.put("ad_password", ad_password);
        jsonObject.put("ad_image", ad_image);
        return jsonObject;
    }

    @Test
    public void addAdmin(HttpServletResponse response) {
        System.out.println("开始测试addAdmin():");

        System.out.println("插入一个符合规范的数据：");
        System.out.println("期望输出：注册成功！！！！");
        JSONObject jsonObject1 = initJSONObject("zzzzzz", "xxxxxx", "cccccc");
        administratorController.addAdmin(jsonObject1, response);

        System.out.println("插入一个数据库中有相同姓名的Administrator");
        System.out.println("期望输出：失败！该用户已存在！");
        JSONObject jsonObject2 = initJSONObject("zzzzzz", "eeeeee", "ssssss");
        administratorController.addAdmin(jsonObject2, response);

        System.out.println("测试结束");
    }

    @Test
    public void getAdminInfo(HttpServletResponse response) {
        System.out.println("开始测试getAdminInfo():");

        System.out.println("查找一个数据库中有的Administrator：");
        System.out.println("期望输出：查找成功!");
        administratorController.getAdminInfo("aaaaaa", response);

        System.out.println("查找一个数据库中没有的Administrator：");
        System.out.println("期望输出：该用户不存在！");
        administratorController.getAdminInfo("bbbbbb", response);

        System.out.println("测试结束");
    }

    @Test
    public void updateAdminInfo(HttpServletResponse response) {
        System.out.println("开始测试updateAdminInfo():");

        System.out.println("正常进行一个修改操作：");
        System.out.println("期望输出：修改成功！");
        JSONObject jsonObject1 = initJSONObject("bbbbbb", "cccccc", "zzzzzz");
        administratorController.updateAdminInfo("aaaaaa", jsonObject1, response);

        System.out.println("修改数据库中不存在的Administrator:");
        System.out.println("期望输出：该用户不存在！");
        JSONObject jsonObject2 = initJSONObject("cccccc", "cccccc", "zzzzzz");
        administratorController.updateAdminInfo("bbbbbb", jsonObject2, response);

        System.out.println("修改为数据库中已存在的Administrator:");
        System.out.println("期望输出：该用户名已被使用，请重新修改！");
        JSONObject jsonObject3 = initJSONObject("uuuuuu", "ffffff", "dddddd");
        administratorController.updateAdminInfo("cccccc", jsonObject3, response);

        System.out.println("测试结束");
    }

}