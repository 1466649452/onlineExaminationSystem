package com.scu.exam.test;

import com.scu.exam.pojo.Classes;
import com.scu.exam.pojo.School;
import com.scu.exam.service.ClassesService;
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
public class ClassesTest {

    @Autowired
    private ClassesService classesService;
    @Test
    public void insertClasses(){
        Classes classes = new Classes();
        classes.setClass_id("29");
        classes.setGrade(150);
        classes.setT_id("1");
        classes.setSchool("双一流大学");
        classes.setStu_number(68);
        int res = classesService.insertClass(classes);
        System.out.println(res);
    }
    @Test
    public void findClassesById(){
        Classes res = classesService.findClassById("29");
        System.out.println(res.toString());
    }
    @Test
    public void updateClass(){
        Classes classes = new Classes();
        classes.setClass_id("29");
        classes.setGrade(100);
        classes.setT_id("1");
        classes.setSchool("双一流大学");
        classes.setStu_number(100);
        int res = classesService.updateClass(classes);
        System.out.println(res);
    }

    @Test
    public void deleteClassById(){
        int res = classesService.deleteClassById("29");
        System.out.println(res);
    }

}
