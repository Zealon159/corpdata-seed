package com.corpdata;

import com.cpda.Application;
import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.entity.OrgUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CorpdataAppApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    // redis 常规测试
    @Test
    public void contextLoads() {

        // string 测试
        //redisUtil.set("a","hello,你好！");
        //System.out.println(redisUtil.get("a"));

        /*OrgUser user = new OrgUser();
        user.setId(1L);
        user.setUserid("admin");
        user.setUserName("管理员");
        redisUtil.set("user",user);*/

        boolean ex = redisUtil.existKey("admin");
        System.out.println(ex);

        redisUtil.expireKey("a",2,TimeUnit.MINUTES);
        //redisUtil.
        //redisUtil.setHashValue("user",user);
    }

}
