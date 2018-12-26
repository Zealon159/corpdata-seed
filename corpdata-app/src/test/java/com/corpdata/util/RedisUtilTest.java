package com.corpdata.util;

import com.cpda.Application;
import com.cpda.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Redis 测试
 * @auther: Zealon
 * @Date: 2018-12-24 16:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplateJackson;

    /**
     * 链表测试
     */
    @Test()
    public void listTest(){
        redisTemplateJackson.opsForList().leftPush("list:queue","速度");
        redisTemplateJackson.opsForList().leftPush("list:queue","力量");
        redisTemplateJackson.opsForList().leftPush("list:queue","智慧");
        Long size = redisTemplateJackson.opsForList().size("list:queue");
        for (int i = 0; i < size; i++) {
            System.out.println(redisTemplateJackson.opsForList().index("list:queue",i));
        }

        redisTemplateJackson.watch("");
    }

    /**
     * Hash 测试
     */
    @Test()
    public void hashTest(){
        // 检查不存在则添加到Hash
        if(!redisUtil.hasHashKey("status","speed")) {
            redisUtil.setHashValue("status", "speed", "速度");
        }

        if(!redisUtil.hasHashKey("status","power")) {
            redisUtil.setHashValue("status", "power", "力量");
        }

        if(!redisUtil.hasHashKey("status","wisdom")) {
            redisUtil.setHashValue("status", "wisdom", "智慧");
        }

        // 获取hash指定key 的值
        Object obj = redisUtil.getHashValue("status","speed");
        System.out.println(obj.toString());

        // 遍历hash
        Map map = redisUtil.getHashEntries("status");

        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = iterator.next();
            System.out.println(entry.getKey()+":"+entry.getValue());
        }

        // 删除
        Long result = redisUtil.deleteHashKeys("status","speed");
        System.out.println("result:"+result);

        Long result1 = redisUtil.deleteHashKeys("status","hhh");
        System.out.println("result1:"+result1);
    }

}
