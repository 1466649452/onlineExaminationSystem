package com.scu.exam.service;

import com.scu.exam.pojo.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TeacherServiceTest {
    @Autowired
    private TeacherService teacherService;

    @Test
    public void insetTest(){

        Teacher teacher1=new Teacher("123","颜老师","123",null);
        Teacher teacher2=new Teacher("456","王老师","456",null);
        List<Teacher> teachers=new ArrayList<Teacher>();
        teachers.add(teacher1);
        teachers.add(teacher2);
        System.out.println(teacherService.insertManyTeacher(teachers));
    }

    @Test
    public void queryTest(){

        Teacher teacher=teacherService.findTeacherById("123");
        System.out.println(teacher);

        /*List<Teacher> teacherList=teacherService.findTeacherByname("颜老师");
        Iterator b=teacherList.iterator();
        while (b.hasNext()){
            System.out.println(b.next());
        }*/
    }

    @Test
    public void updateTest(){
        //teacherService.updateTeacherName("123","yan老师");
        //teacherService.updateTeacherPassword("123","321");
        //teacherService.updateTeacherHeadimage("123","head");

        teacherService.updateTeacher("123","颜老师","123",null);
    }

    @Test
    public void deleteTest(){
        teacherService.deleteTeacherByid("123");
    }
}
