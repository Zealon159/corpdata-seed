package com.corpdata.system.org.dao;

import java.util.List;
import java.util.Map;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgRolePermission;

public interface OrgRolePermissionMapper extends BaseMapper<OrgRolePermission> {
	
    int deleteByRoleId(String roleId);

    List<OrgRolePermission> selectAll();
    
    List<OrgRolePermission> getOrgRolePermissionMapList(Map<String, Object> params);
}