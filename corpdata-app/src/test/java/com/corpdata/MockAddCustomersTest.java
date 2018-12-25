package com.corpdata;

import com.corpdata.task.AddCustomerTask;
import com.corpdata.task.AddCustomersTask;
import com.corpdata.util.PhoneUtil;
import com.cpda.Application;
import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟添加客户
 * @auther: Zealon
 * @Date: 2018-12-24 10:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockAddCustomersTest {

    @Autowired
    private PhoneMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    // 客户端并发量
    private int clientCount = 10;
    // 轮询次数
    private int polling = 200;

    /**
     * 模拟并发（数据库排重检查）
     */
    @Test()
    public void addCustomerDBChecked() {
        addCustomer(false);
    }

    /**
     * 模拟并发（Redis 排重检查）
     */
    @Test()
    public void addCustomerRedisChecked() {
        addCustomer(true);
    }

    public void addCustomer(boolean useRedis)  {
        long startTime = System.currentTimeMillis();
        AtomicInteger repeatCount = new AtomicInteger(); // 重复计数器
        AtomicInteger failCount = new AtomicInteger();   // 失败计数器
        Semaphore semaphore = new Semaphore(clientCount);
        CountDownLatch countDownLatch = new CountDownLatch(polling*clientCount); //线程计数器
        ExecutorService executorService = Executors.newFixedThreadPool(15);

        for (int j = 1; j <= polling; j++) {
            // 模拟客户端并发保存数据
            for (int i = 1; i <= clientCount; i++) {
                String phone = PhoneUtil.getPhoneNumber();
                int id = new Random().nextInt(9999999);
                AddCustomersTask task = new AddCustomersTask(phone,id,useRedis,semaphore,countDownLatch,mapper,redisUtil,repeatCount,failCount);

                // 执行任务
                executorService.execute(task);
            }
        }

        // 等待线程任务执行完成
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 计算耗时
        long entTime = System.currentTimeMillis();
        long runTime = entTime-startTime;//(int)(entTime-startTime)/1000;
        System.out.println("执行完成. runTime:"+runTime+"ms. 重复数："+repeatCount.get()+". 失败数："+failCount);
    }
}
