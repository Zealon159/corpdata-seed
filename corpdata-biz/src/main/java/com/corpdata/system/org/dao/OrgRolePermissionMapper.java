package com.corpdata.system.org.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.corpdata.system.org.entity.OrgRolePermission;

@Mapper
public interface OrgRolePermissionMapper {
    int deleteByRoleId(String roleId);

    int insert(OrgRolePermission record);

    OrgRolePermission selectByPrimaryKey(String id);

    List<OrgRolePermission> selectAll();

    int updateByPrimaryKey(OrgRolePermission record);
    
    List<OrgRolePermission> getOrgRolePermissionMapList(Map<String, Object> params);
}