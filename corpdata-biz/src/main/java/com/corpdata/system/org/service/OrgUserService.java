package com.corpdata.system.org.service;

import java.util.Set;
import com.corpdata.common.result.Result;
import com.corpdata.core.base.BaseService;
import com.corpdata.system.org.entity.OrgUser;

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
	
	Result insert(OrgUser record,String orgDeptId);
	Result update(OrgUser record,String orgDeptId);
	
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	Result modPassword(String id, String newPassword);
}
