package com.scu.exam.test;

import com.scu.exam.pojo.School;
import com.scu.exam.service.SchoolService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class schoolTest {
    @Autowired
    private SchoolService schoolService;
    @Test
    public void insertSchool(){
        School school =new School();
        school.setSchool_name("sichuanuniversity");
        school.setSchool_address("shuangliuqu");
        school.setSchool_phone("66666666");
        int res = schoolService.insertSchool(school);
        System.out.println(res);
    }
    @Test
    public void findSchoolByName(){
        School res = schoolService.findSchoolByName("sichuanuniversity");
        System.out.println(res.toString());
    }
    @Test
    public void updateSchool(){
        School school =new School();
        school.setSchool_name("sichuanuniversity");
        school.setSchool_address("wangjiangxiaoqu");
        school.setSchool_phone("77777777");
        int res = schoolService.updateSchool(school);
        System.out.println(res);
    }

    @Test
    public void deleteSchoolByName(){
        int res = schoolService.deleteSchoolByName("sichuanuniversity");
        System.out.println(res);
    }

}
