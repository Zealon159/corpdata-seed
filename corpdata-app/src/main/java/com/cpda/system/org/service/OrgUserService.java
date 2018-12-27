package com.cpda.system.org.service;

import com.cpda.common.base.BaseService;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgUser;

import java.util.Set;

public interface OrgUserService extends BaseService<OrgUser> {
	/**
	 * 获取当前用户所有角色
	 */
	Set<String> getRolesByUser(String userId);
	
	/**
	 * 获取当前用户所有权限
	 */
	Set<String> getPermissionsByUser(String userId);
	
	/***
	 * 根据用户id获取用户信息
	 */
	OrgUser getUserInfoByUserid(String userId);

	Result insert(OrgUser record);

	Result update(OrgUser record);
	
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	Result modPassword(Long id, String newPassword);

	String findByCombox();

	String findByPage(int page, int rows, Long deptId, String keyWord);

	Result deleteByUserid(String userId);
}
