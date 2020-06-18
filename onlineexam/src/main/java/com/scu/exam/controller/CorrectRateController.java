package com.scu.exam.controller;

import com.scu.exam.service.CorrectRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CorrectRateController {

    @Autowired
    private CorrectRateService correctRateService;
}
