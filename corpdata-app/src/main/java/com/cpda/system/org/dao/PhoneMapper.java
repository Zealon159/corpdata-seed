package com.cpda.system.org.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @auther: Zealon
 * @Date: 2018-12-21 16:36
 */
@Mapper
public interface PhoneMapper {

    @Insert("insert into customer_phone(phone,customerId) values(#{phone},#{id})")
    public int addPhone(@Param("phone") String phone, @Param("id") int id);

    @Select("select count(1) from customer_phone where phone=#{phone}")
    public int checkRepeat(@Param("phone") String phone);

    @Select("select * from customer_phone limit #{start},#{end}")
    public List<Map<String,Object>> selectAll(@Param("start") int start,@Param("end") int end);
}
