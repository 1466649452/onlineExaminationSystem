package com.scu.exam.service;

import com.scu.exam.pojo.CorrectRate;
import com.scu.exam.service.CorrectRateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class CorrectRateServiceTest {

    @Autowired
    private CorrectRateService correctRateService;

    @Test
    public void insertCorrectRateTest(){
        CorrectRate correctRate = new CorrectRate();
        correctRate.setQuestion_id(1);
        correctRate.setPaper_id(1);
        correctRate.setAccuracy(0.93);
        correctRate.setRes_number(40);
        correctRate.setPoint(5);
        int result = correctRateService.insertCorrectRate(correctRate);
        System.out.println(result);
    }

    @Test
    public void insertCorrectRateBatchTest(){
        List<CorrectRate> correctRateList = new ArrayList<CorrectRate>();
        for(int i=1;i<=3;i++){
            CorrectRate correctRate = new CorrectRate();
            correctRate.setQuestion_id(i);
            correctRate.setPaper_id(2);
            correctRate.setPoint(5);
            correctRateList.add(correctRate);
        }
        //System.out.println(correctRateList);
        int result = correctRateService.insertCorrectRateBatch(correctRateList);
        System.out.println(result);
    }

    @Test
    public void updateCorrectRateTest(){
        CorrectRate correctRate = new CorrectRate();
        correctRate.setPoint(6);
        correctRate.setQuestion_id(1);
        correctRate.setPaper_id(1);
        int result = correctRateService.updateCorrectRate(correctRate);
        System.out.println(result);
    }

    @Test
    public void findByAllIdTest(){
        CorrectRate correctRate = correctRateService.findByAllId(1,1);
        System.out.println(correctRate);
    }

    @Test
    public void findByPidTest(){
        List<CorrectRate> result = correctRateService.findByPid(1);
        System.out.println(result);
    }

    @Test
    public void deleteCorrectRateTest(){
        int result = correctRateService.deleteCorrectRate(1,1);
        System.out.println(result);
    }

    @Test
    public void deleteCorrectRateBatchTest(){
        int result = correctRateService.deleteCorrectRateBatch(1);
        System.out.println(result);
    }


}
