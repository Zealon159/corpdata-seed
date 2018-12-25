package com.cpda.system.org.service;

import com.cpda.common.result.Result;

/**
 * 角色权限关联
 * @auther: Zealon
 * @Date: 2018-12-20 15:38
 */
public interface OrgRolePermissionService {
    /**
     * 保存角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    Result saveRolePermission(long roleId, String permissionIds);

    /**
     * 删除角色权限
     * @param roleId
     * @return
     */
    void deleteRolePermissionByRoleId(long roleId);

    /**
     * 获取当前角色所有权限
     * @param roleId
     * @return
     */
    String getOrgRolePermissionMapList(long roleId);
}
