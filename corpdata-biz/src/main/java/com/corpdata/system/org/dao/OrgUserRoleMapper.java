package com.corpdata.system.org.dao;

import org.apache.ibatis.annotations.Mapper;

import com.corpdata.system.org.entity.OrgUserRole;
 

@Mapper
public interface OrgUserRoleMapper {
    int deleteByPrimaryKey(String id);
    
    int deleteByUserid(String userid);

    int insert(OrgUserRole record);

    int insertSelective(OrgUserRole record);

    OrgUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrgUserRole record);

    int updateByPrimaryKey(OrgUserRole record);
}