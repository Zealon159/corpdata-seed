package com.corpdata.task;

import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther: Zealon
 * @Date: 2018-12-24 11:04
 */
public class AddCustomerTask implements Runnable {

    private PhoneMapper mapper;
    private RedisUtil redisUtil;
    private CountDownLatch countDownLatch;
    private String phone;
    private int customerId;
    private boolean useRedis;
    private AtomicInteger repeatCount;
    private AtomicInteger failCount;

    @Override
    public void run() {
        try {
            // 是否有重复
            boolean repeat = false;
            String mode = "";
            if (useRedis) {
                // 基于redis 排重
                repeat = redisUtil.existInSet("phones", phone);
                mode = "redis 排重";
            } else {
                // 基于mysql 排重
                repeat = mapper.checkRepeat(phone) > 0;
                mode = "mysql 排重";
            }

            if (repeat) {
                repeatCount.addAndGet(1);
                System.out.println("重复:" + phone);
            } else {
                //mapper.addPhone(phone, customerId);
                //redisUtil.addSetValue("phones",phone);
                //System.out.println(Thread.currentThread().getName() + ":" + phone + " saved."+mode);
            }
        } catch (Exception ex) {
            // 失败数量 +1
            failCount.addAndGet(1);
        } finally {
            // 执行完成,计数器+1
            countDownLatch.countDown();
        }
    }

    public AddCustomerTask(){

    }

    public AddCustomerTask(String phone, int customerId,boolean useRedis,CountDownLatch countDownLatch,PhoneMapper mapper,RedisUtil redisUtil,AtomicInteger repeatCount,AtomicInteger failCount) {
        this.phone = phone;
        this.customerId = customerId;
        this.useRedis = useRedis;
        this.countDownLatch = countDownLatch;
        this.mapper = mapper;
        this.redisUtil = redisUtil;
        this.repeatCount = repeatCount;
        this.failCount = failCount;
    }
}
