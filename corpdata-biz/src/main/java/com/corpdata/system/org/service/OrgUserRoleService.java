package com.corpdata.system.org.service;

import com.corpdata.common.result.Result;
import com.corpdata.core.base.BaseService;
import com.corpdata.system.org.entity.OrgUserRole;

public interface OrgUserRoleService extends BaseService<OrgUserRole> {
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	Result createUserRoles(String userId,String roles);
}
