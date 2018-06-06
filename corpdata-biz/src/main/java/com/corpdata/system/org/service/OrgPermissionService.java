package com.corpdata.system.org.service;

import java.util.List;

import com.corpdata.system.org.entity.OrgRolePermission;

public interface OrgPermissionService {
	/**
	 * 获取权限树JSON
	 * @return
	 */
	String selectAllTreeJsonData();
	/**
	 * @author cy
	 * @return
	 * @description 获取角色权限映射集合
	 */
	List<OrgRolePermission> getOrgRolePermissionMapList(String key,String value);
	/**
	 * @author cy
	 * @param roleId
	 * @return
	 * @description 通过角色id查询权限信息
	 */
	String selectPermissionIdByRoleId(String roleId);
}
