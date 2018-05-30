package com.corpdata.system.org.dao;

import com.corpdata.system.org.entity.OrgRole;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrgRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrgRole record);

    OrgRole selectByPrimaryKey(String id);

    Page<OrgRole> selectAll(Map<String, Object> params);

    int updateByPrimaryKey(OrgRole record);
    
    List<String> getRolesByUser(String userid);
    
    List<OrgRole> findOrgRoleList(Map<String, Object> params);
}