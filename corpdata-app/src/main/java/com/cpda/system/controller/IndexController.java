package com.cpda.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther: Zealon
 * @Date: 2018-08-20 12:38
 */
@Controller
public class IndexController {

    @RequestMapping("/system")
    public String index(){
        return "system/index";
    }
}
