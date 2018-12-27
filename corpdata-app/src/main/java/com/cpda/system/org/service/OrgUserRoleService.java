package com.cpda.system.org.service;

import com.cpda.common.base.BaseService;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgUserRole;

import java.util.List;

/**
 * 用户角色关系映射
 */
public interface OrgUserRoleService extends BaseService<OrgUserRole> {
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	Result createUserRoles(String userId, String roles);

	List<OrgUserRole> findByUserId(String userid);

	String findRoleIdsByUserId(String userid);

	void deleteByUserId(String userid);
}
