package com.corpdata.system.org.service;

import com.corpdata.common.result.Result;

public interface OrgUserRoleService {
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	Result createUserRoles(String userId,String roles);
}
