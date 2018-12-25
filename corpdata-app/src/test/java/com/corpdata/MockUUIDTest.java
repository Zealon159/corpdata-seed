package com.corpdata;

import com.cpda.Application;
import com.cpda.system.org.dao.PhoneMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @Author: zealon
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockUUIDTest {

    //@Autowired
    //private PhoneMapper mapper;

    @Test()
    public void contextLoads() {

        for (int i = 11; i < 1000000; i++) {
            String uuid = java.util.UUID.randomUUID().toString().replace("-","");
            String phone = getPhoneHead()+getPhoneContent();
            //mapper.addUUIDPhone(uuid,phone,i);
        }

    }

    /**
     * 获取手机号 前3位
     * @return
     */
    private String getPhoneHead(){
        int[] twoArr = {3,5,7,8};
        String two = "" + twoArr[new Random().nextInt(4)];
        String three = ""+new Random().nextInt(10);
        return "1"+two+three;
    }


    /**
     * 获取手机号 后8位
     * @return
     */
    private String getPhoneContent(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            sb.append(new Random().nextInt(10));
        }
        return sb.toString();
    }
}
