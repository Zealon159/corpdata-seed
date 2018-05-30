package com.corpdata.system.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.corpdata.system.org.entity.OrgPermission;

@Mapper
public interface OrgPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrgPermission record);

    OrgPermission selectByPrimaryKey(String id);

    List<OrgPermission> selectAll();

    int updateByPrimaryKey(OrgPermission record);
    
    List<String> getPermissionsByUser(String userid);
    
}