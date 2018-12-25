package com.corpdata.util;

import java.util.Random;

/**
 * @Author: zealon
 * @Version: 1.0
 */
public class PhoneUtil {
    /**
     * 获取手机号
     * @return
     */
    public static String getPhoneNumber(){
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
