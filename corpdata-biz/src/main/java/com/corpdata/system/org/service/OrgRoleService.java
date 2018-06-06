package com.corpdata.system.org.service;

import java.util.List;
import com.corpdata.common.result.Result;
import com.corpdata.system.org.entity.OrgRole;

public interface OrgRoleService {
	/**
	 * 保存角色权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	Result saveRolePermission(String roleId,String permissionIds);
	
	List<OrgRole> findOrgRoleList();
	
	List<String> getRolesByUser(String userid);
}
