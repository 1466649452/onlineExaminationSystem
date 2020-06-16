package com.scu.exam.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class ScoreServiceTest {

    @Autowired
    private ScoreService scoreService;

    @Test
    public void using() throws IOException {
        com.scu.exam.pojo.Score a=scoreService.findOneScore(1);
        System.out.println(a);
        System.out.println("这就成功了？");
    }


}
