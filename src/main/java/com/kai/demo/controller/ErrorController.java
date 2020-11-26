package com.kai.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/noLogin")
    public String noLogin() {
        return "noLogin";
    }
}
