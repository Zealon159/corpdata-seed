package com.corpdata.util;

import com.cpda.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 队列消费者测试
 * @auther: Zealon
 * @Date: 2018-12-24 17:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RedisUtilListConsumerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test()
    public void consumerTest(){

        while (true) {
            Object obj = redisTemplate.opsForList().rightPopAndLeftPush("list:queue","list:new-queue");
            //Object obj = redisTemplate.opsForList().rightPop("list:queue");
            if (obj!=null) {
                System.out.println(obj.toString());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
