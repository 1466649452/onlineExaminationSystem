package com.scu.exam.controller;

import com.scu.exam.pojo.Score;
import com.scu.exam.service.ScoreService;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "获取信息")
@Controller
@RequestMapping("info")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @ApiOperation("获取考生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "stu_id", dataType = "String", required = true, value = "用户Id")
    })
    @GetMapping("/getScore")
    public void getScoreInfo(Integer stu_id, HttpServletRequest request, HttpServletResponse response){
        System.out.println(request);

        System.out.println("hello");
        System.out.println(stu_id);
        Score b=scoreService.findOneScore(1);
        System.out.println(b);
        Score a=scoreService.findOneScore(stu_id);
        System.out.println(a);
        System.out.println("借宿");


        ResponseUtils.renderJson(response,"真的可以哦");
    }


}
