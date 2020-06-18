package com.scu.exam.service;

import com.scu.exam.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    public void findByTidTest() {
       List<com.scu.exam.pojo.Test> result = testService.findByTid("t_01");
        System.out.println(result.get(0));
        System.out.println(result.get(0).getStart());
        System.out.println(result);
    }

    @Test
    public void insertTest()  {
       com.scu.exam.pojo.Test test = new com.scu.exam.pojo.Test();
        test.setPaper_name("jiujiu");
        test.setT_id("1");

        //String类型格式化为timestamp
        String date = "2009-07-16 19:20:33"; // <input type="datetime-local"> 输入参数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt = sdf.parse(date);
            test.setStart(new Timestamp(dt.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = testService.insertTest(test);
        System.out.println(result);
        System.out.println("返回的主键:"+test.getPaper_id());
    }

    //将timestamp类型格式化为String
    //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    // System.out.println(stu.getCreateTime()); //2009-07-16 19:20:00.0
    //System.out.println(sdf.format(stu.getCreateTime())); //2009/07/16 19:20

    @Test
    public void updateTest(){
        com.scu.exam.pojo.Test test = new com.scu.exam.pojo.Test();
        test.setPaper_id(0);
        test.setPaper_name("jiujiu");
        int result = testService.updateTest(test);
        System.out.println(result);

    }

    @Test
    public void deleteTest(){
        int result = testService.deleteTest(0);
        System.out.println(result);
    }

    @Test
    public void deleteAllByTid(){
        int result = testService.deleteAllByTid("t_01");
        System.out.println(result);
    }




}
