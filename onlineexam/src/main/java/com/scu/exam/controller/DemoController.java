package com.scu.exam.controller;

import com.alibaba.fastjson.JSONObject;
import com.scu.exam.pojo.Score;
import com.scu.exam.utils.ResponseUtils;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;

@Api(tags = "用户模块")
@Controller
public class DemoController {
    @ApiOperation("使用responsebody返回对象数据到前端")
    @ApiImplicitParams({
            /*
             * 此处为参数列表
             * */
            @ApiImplicitParam(paramType = "query", name = "userId", dataType = "String", required = true, value = "用户Id")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    /*
     * @RespinseBody会将方法的返回值转换为json或xml传递到 httprespons中返回到前端
     * */
    @ResponseBody
    @GetMapping("/list")
    public void list(HttpServletResponse response) {
        System.out.println("hello");
        Score score = new Score("1", 1, 98.5, new Date(), null);
        ResponseUtils.renderJson(response,score.toString());
    }


    @ApiOperation("采用往json中添加数据后返回")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对")
    })
    /*
     * @RespinseBody会将方法的返回值转换为json或xml传递到 httprespons中返回到前端
     * @RequestPara用head中的数据，即get方法吧数据放到url中
     * ajax中data中的只需要使用@RequestBody来传递
     *  模版在这里奥
     * */
    @ResponseBody
    @PostMapping("/addValue")
    public void addValue(@RequestBody JSONObject data, HttpServletResponse response) {
        //前段通过post方法请求数据
        System.out.println(data);
        System.out.println(data.get("information"));

        //新建一个jsonobject返回到前端
        JSONObject value = new JSONObject();
        value.put("respo", "success");
        value.put("name", "王");

        ResponseUtils.renderJson(response, value);
    }


}
