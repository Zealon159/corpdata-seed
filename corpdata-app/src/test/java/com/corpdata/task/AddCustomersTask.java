package com.corpdata.task;

import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.PhoneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther: Zealon
 * @Date: 2018-12-24 11:04
 */
public class AddCustomersTask implements Runnable {

    private PhoneMapper mapper;
    private RedisUtil redisUtil;
    private Semaphore semaphore;
    private CountDownLatch countDownLatch;
    private String phone;
    private int customerId;
    private boolean useRedis;
    private AtomicInteger repeatCount;
    private AtomicInteger failCount;

    @Override
    public void run() {
        try {
            // 获取许可
            semaphore.acquire();

            // 是否有重复
            boolean repeat = false;
            if (useRedis) {
                // 基于redis 排重
                repeat = redisUtil.existInSet("phones", phone);
            } else {
                // 基于mysql 排重
                repeat = mapper.checkRepeat(phone) > 0;
            }

            if (repeat) {
                repeatCount.addAndGet(1);
                System.out.println("重复:" + phone);
            } else {
                //mapper.addPhone(phone, customerId);
                //redisUtil.addSetValue("phones",phone);
                System.out.println(Thread.currentThread().getName() + ":" + phone + " saved.");
            }
        } catch (Exception ex) {
            // 失败数量 +1
            failCount.addAndGet(1);
        } finally {
            // 执行完成，释放许可
            semaphore.release();
            countDownLatch.countDown();
        }
    }

    public AddCustomersTask(){

    }

    public AddCustomersTask(String phone, int customerId, boolean useRedis, Semaphore semaphore, CountDownLatch countDownLatch , PhoneMapper mapper, RedisUtil redisUtil, AtomicInteger repeatCount, AtomicInteger failCount) {
        this.phone = phone;
        this.customerId = customerId;
        this.useRedis = useRedis;
        this.semaphore = semaphore;
        this.countDownLatch = countDownLatch;
        this.mapper = mapper;
        this.redisUtil = redisUtil;
        this.repeatCount = repeatCount;
        this.failCount = failCount;
    }
}
