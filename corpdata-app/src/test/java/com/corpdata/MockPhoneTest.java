package com.corpdata;

import com.corpdata.util.PhoneUtil;
import com.cpda.Application;
import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @auther: Zealon
 * @Date: 2018-12-21 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockPhoneTest {

    @Autowired
    private PhoneMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 初始化手机号
     */
    @Test()
    public void initPhone() {
        long startTime = System.currentTimeMillis();

        for (int i = 500001; i <= 600000; i++) {
            String phone = PhoneUtil.getPhoneNumber();
            // 数据库排重
            if (mapper.checkRepeat(phone)==0){
                mapper.addPhone(phone,i);
            }else{
                // 有重复，重新计算
                System.out.println(i+" 重复:"+phone);
                i--;
            }
        }

        // 计算耗时
        long entTime = System.currentTimeMillis();
        int runTime = (int)(entTime-startTime)/1000;
        System.out.println("runTime:"+runTime+". s");
    }

    /**
     * 初始化手机号
     */
    @Test()
    public void initPhoneByRedis() {
        long startTime = System.currentTimeMillis();
        for (int i = 700001; i <= 1000000; i++) {
            String phone = PhoneUtil.getPhoneNumber();
            // redis 排重
            boolean exist = redisUtil.existInSet("phones",phone);
            if (!exist){
                mapper.addPhone(phone,i);
                redisUtil.addSetValue("phones",phone);
            }else{
                // 有重复，重新计算
                System.out.println(i+" 重复:"+phone);
                i--;
            }
        }

        long entTime = System.currentTimeMillis();
        int runTime = (int)(entTime-startTime)/1000;
        System.out.println("runTime:"+runTime);
    }

    /**
     * 将数据库中的手机号 初始化redis中
     */
    @Test()
    public void loadPhoneToRedis(){
        List<Map<String,Object>> phones = mapper.selectAll(0,600001);
        System.out.println(phones.size());
        for (int i = 0; i < phones.size(); i++) {
            Map<String,Object> phone = phones.get(i);
            //System.out.println(phone.get("phone"));
            redisUtil.addSetValue("phones",phone.get("phone"));
        }
    }

    @Test()
    public void getRedisPhones(){

        // 判断是否存在
        String phone = "13000016830";
        boolean exist = redisUtil.existInSet("phones",phone);
        System.out.println(phone+":"+exist);

        // 遍历
        /*Set phones = redisUtil.getSetValues("phones");
        Iterator iterator = phones.iterator();
        while(iterator.hasNext()){
            Object phone = iterator.next();
            System.out.println(phone);
        }*/
    }
}
