package com.cpda.system.easyui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * EsayUI Demo Pages
 * @auther: Zealon
 * @Date: 2018-08-23 15:12
 */
@Controller
@RequestMapping("easyui/demo")
public class DemoController {

    @GetMapping("/icons")
    public String icons(){
        return "system/demo/icons";
    }

    @GetMapping("/button")
    public String button(){
        return "system/demo/button";
    }

    @GetMapping("/form")
    public String form(){
        return "system/demo/form";
    }
}
