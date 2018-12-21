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
 * @auther: Zealon
 * @Date: 2018-12-21 17:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MockPhoneTest {

    @Autowired
    private PhoneMapper mapper;

    @Test()
    public void contextLoads() {

        for (int i = 100001; i <= 200000; i++) {
            String phone = getPhoneHead()+getPhoneContent();
            if (mapper.checkRepeat(phone)==0){
                mapper.addPhone(phone,i);
            }else{
                // 有重复，重新计算
                System.out.println(i+" 重复:"+phone);
                i--;
            }
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
