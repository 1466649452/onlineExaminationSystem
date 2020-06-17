package com.scu.exam.test;

import com.scu.exam.pojo.School;
import com.scu.exam.pojo.Student;
import com.scu.exam.service.SchoolService;
import com.scu.exam.service.StudentService;
import com.scu.exam.service.impl.StudentServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class studentTest {
    @Autowired
    private StudentService studentService;


    @Test
    public void insertStudentTest(){
        Student b = new Student();
        b.setStu_id("2020");
        b.setStu_idcard("1111111");
        b.setClass_id("1");
        b.setStu_name("增加");
        b.setStu_password("123");
        b.setStu_Image("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dstr="2008-04-24";
        try {
            Date dt = sdf.parse(dstr);
            b.setStu_birthdate(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result= studentService.insertStudent(b);
        System.out.println(result);
    }

    @Test
    public void searchStudentTest(){
        Student result= studentService.findStudentById("2020");
        System.out.println(result.toString());
        List<Student> studentList = studentService.findStudentByName("增加");
        System.out.println(studentList.toString());
    }

    @Test
    public void updateStudentTest(){
        Student b = new Student();
        b.setStu_id("2020");
        b.setStu_idcard("1234567");
        b.setClass_id("1");
        b.setStu_name("修改");
        b.setStu_password("123");
        b.setStu_Image("1");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dstr="2008-04-24";
        try {
            Date dt = sdf.parse(dstr);
            b.setStu_birthdate(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result= studentService.updateStudent(b);
        System.out.println(result);
    }


    @Test
    public void deleteStudentTest(){
        int result= studentService.deleteStudentById("2020");
        System.out.println(result);
    }


}