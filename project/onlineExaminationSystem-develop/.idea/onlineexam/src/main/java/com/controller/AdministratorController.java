package com.controller;

import com.pojo.Administrator;
import com.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;


import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("controller")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @GetMapping("/testAdministratorFind")
    public String findAdministratorById(HttpServletRequest httpServletRequest){
        Administrator administrator = new Administrator("zzzzzz", "xxxxxx", "cccccc","");
        administratorService.insertAdministrator(administrator);
        Administrator administrator1 = administratorService.findAdministratorById("zzzzzz");
        if (null != administrator1){
            httpServletRequest.setAttribute("administrator",administrator1);
        }
        System.out.println(administrator1.toString());
        return "test";
    }
}
