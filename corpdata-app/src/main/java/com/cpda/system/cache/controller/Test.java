package com.cpda.system.cache.controller;


import com.cpda.common.utils.SerializeUtils;

/**
 * @auther: Zealon
 * @Date: 2018-12-07 17:27
 */
public class Test {
    public static void main(String[] args){
        String name = "哈哈哈";
        byte[] sName = SerializeUtils.serialize(name);
        System.out.println(sName);
    }
}
