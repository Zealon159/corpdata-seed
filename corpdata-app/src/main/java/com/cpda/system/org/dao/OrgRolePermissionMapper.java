package com.cpda.system.org.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.org.entity.OrgRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 角色权限映射
 */
public interface OrgRolePermissionMapper extends BaseMapper<OrgRolePermission> {
	
    int deleteByRoleId(long roleId);

    List<OrgRolePermission> selectAll();

    /**
     * 角色查询所有权限
     * @param roleId
     * @return
     */
    Set<Long> getOrgRolePermissionSet(@Param("roleId") Long roleId);
}