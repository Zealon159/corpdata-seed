package com.corpdata.system.org.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.corpdata.system.org.dao.OrgUserRoleMapper;
import com.corpdata.system.org.entity.OrgRole;
import com.corpdata.system.org.entity.OrgUserRole;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractBaseService;

/**
 * 用户角色关联服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Service("OrgUserRoleService")
public class OrgUserRoleService extends AbstractBaseService<OrgUserRole>{
	
	@Autowired
	private OrgUserRoleMapper orgUserRoleMapper;
	
	
	public OrgUserRole selectByPrimaryKey(String id){
		return orgUserRoleMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	public Result createUserRoles(String userId,String roles) {
		Result r = ResultUtil.fail();
		try{
			//先删除用户角色，再进行批量新增
			orgUserRoleMapper.deleteByUserid(userId);
			JSONArray rolesArray = JSON.parseArray(roles);
			for(int i=0;i<rolesArray.size();i++){
				OrgUserRole userRole = new OrgUserRole();
				String roleId = (String)rolesArray.get(i);
				userRole.setId(CorpdataUtil.getUUID());
				OrgRole role = new OrgRole();
				role.setId(roleId);
				userRole.setOrgRole(role);
				userRole.setUserid(userId);
				if (orgUserRoleMapper.insert(userRole)>0) {
					r = ResultUtil.success();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			r = ResultUtil.error();
		}
		return r;
	}
}
