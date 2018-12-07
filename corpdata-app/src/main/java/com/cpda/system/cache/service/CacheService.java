package com.cpda.system.cache.service;

import com.cpda.common.api.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @auther: Zealon
 * @Date: 2018-12-07 14:36
 */
@Service
public class CacheService {

    @Autowired
    private RedisService redisService;

    public Object getKey(String key){
        return redisService.get(key);
    }

    public void setKey(String key,Object value){
        redisService.set(key,value);
    }

    public Object getObject(String key){
        return redisService.getObject(key);
    }
}
