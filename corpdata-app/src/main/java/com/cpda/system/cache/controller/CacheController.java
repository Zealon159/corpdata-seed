package com.cpda.system.cache.controller;

import com.cpda.system.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @auther: Zealon
 * @Date: 2018-12-07 14:40
 */
@Controller
@RequestMapping("system/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/setkey")
    @ResponseBody
    public String setKey(String key,Object value){
        cacheService.setKey(key,value);
        return "ok";
    }

    @PostMapping("/getkey")
    @ResponseBody
    public Object getKey(String key){
        Object value = cacheService.getKey(key);
        return value;
    }
}
