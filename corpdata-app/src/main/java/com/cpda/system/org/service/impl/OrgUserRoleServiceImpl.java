package com.cpda.system.org.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.system.org.dao.OrgUserRoleMapper;
import com.cpda.system.org.entity.OrgRole;
import com.cpda.system.org.entity.OrgUserRole;
import com.cpda.system.org.service.OrgUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色关联服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Transactional
@Service("OrgUserRoleService")
public class OrgUserRoleServiceImpl extends AbstractBaseService<OrgUserRole> implements OrgUserRoleService {
	
	@Autowired
	private OrgUserRoleMapper orgUserRoleMapper;
	
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	public Result createUserRoles(String userId, String roles) {
		Result r = ResultUtil.success();
		try{
			//先删除用户角色，再进行批量新增
			orgUserRoleMapper.deleteByUserid(userId);

			JSONArray rolesArray = JSON.parseArray(roles.replace("&quot;", "\""));
			for(int i=0;i<rolesArray.size();i++){
				OrgUserRole userRole = new OrgUserRole();
				long roleId = Long.parseLong(rolesArray.get(i).toString());
				OrgRole role = new OrgRole();
				role.setId(roleId);
				userRole.setOrgRole(role);
				userRole.setUserid(userId);
				orgUserRoleMapper.insert(userRole);
			}
		}catch(Exception e){
			e.printStackTrace();
			r = ResultUtil.error();
		}
		return r;
	}

	@Override
	public List<OrgUserRole> findByUserId(String userid) {
		return orgUserRoleMapper.selectByUserId(userid);
	}

	@Override
	public void deleteByUserId(String userid) {
		orgUserRoleMapper.deleteByUserid(userid);
	}
}
