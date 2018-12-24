package com.corpdata;

import com.corpdata.task.AddCustomerTask;
import com.cpda.Application;
import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟添加客户
 * @auther: Zealon
 * @Date: 2018-12-24 10:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockAddCustomerTest {

    @Autowired
    private PhoneMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    // 客户端并发量
    private int clientCount = 100;
    // 轮询次数
    private int polling = 200;

    /**
     * 模拟并发（数据库排重检查）
     */
    @Test()
    public void addCustomerDBChecked() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        AtomicInteger repeatCount = new AtomicInteger(); // 重复计数器
        AtomicInteger failCount = new AtomicInteger();   // 失败计数器
        CountDownLatch countDownLatch = new CountDownLatch(clientCount * polling); //线程计数器
        ExecutorService executorService = Executors.newFixedThreadPool(15);

        for (int j = 1; j <= polling; j++) {
            // 模拟客户端并发保存数据
            for (int i = 1; i <= clientCount; i++) {
                String phone = getPhoneNumber();
                int id = new Random().nextInt(9999999);
                AddCustomerTask task = new AddCustomerTask(phone,id,false,countDownLatch,mapper,redisUtil,repeatCount,failCount);

                // 执行任务
                executorService.execute(task);
            }
        }

        // 等待线程任务执行完成
        countDownLatch.await();

        // 计算耗时
        long entTime = System.currentTimeMillis();
        long runTime = entTime-startTime;//(int)(entTime-startTime)/1000;
        System.out.println("执行完成. runTime:"+runTime+"ms. 重复数："+repeatCount.get()+". 失败数："+failCount);
    }

    /**
     * 模拟并发（Redis 排重检查）
     */
    @Test()
    public void addCustomerRedisChecked() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        AtomicInteger repeatCount = new AtomicInteger(); // 重复计数器
        AtomicInteger failCount = new AtomicInteger();   // 失败计数器
        CountDownLatch countDownLatch = new CountDownLatch(clientCount*polling); //线程计数器
        ExecutorService executorService = Executors.newFixedThreadPool(15);

        for (int j = 1; j <= polling; j++) {
            // 模拟客户端并发保存数据
            for (int i = 1; i <= clientCount; i++) {
                String phone = getPhoneNumber();
                int id = new Random().nextInt(9999999);
                AddCustomerTask task = new AddCustomerTask(phone,id,true,countDownLatch,mapper,redisUtil,repeatCount,failCount);

                // 执行任务
                executorService.execute(task);
            }
        }

        // 等待线程任务执行完成
        countDownLatch.await();

        // 计算耗时
        long entTime = System.currentTimeMillis();
        long runTime = entTime-startTime;//(int)(entTime-startTime)/1000;
        System.out.println("执行完成. runTime:"+runTime+"ms. 重复数："+repeatCount.get()+". 失败数："+failCount);
    }

    /**
     * 获取手机号
     * @return
     */
    private String getPhoneNumber(){
        StringBuffer phone = new StringBuffer();

        // 前3位
        int[] twoArr = {3,5,7,8};
        phone.append("1");
        phone.append(twoArr[new Random().nextInt(4)]);
        phone.append(new Random().nextInt(10));

        // 后8位
        for (int i = 0; i < 8; i++) {
            phone.append(new Random().nextInt(10));
        }
        return phone.toString();
    }

}
