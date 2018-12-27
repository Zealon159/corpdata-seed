package com.cpda.system.org.service.impl;

import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.common.utils.RedisUtil;
import com.cpda.system.org.dao.OrgUserRoleMapper;
import com.cpda.system.org.entity.OrgRole;
import com.cpda.system.org.entity.OrgUserRole;
import com.cpda.system.org.service.OrgUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.cpda.config.GlobalConstant;

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

	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 创建用户角色
	 * @userId 用户Id
	 * @roles 角色IDs
	 */
	public Result createUserRoles(String userId, String roles) {
		Result r = ResultUtil.success();
		try{
			// 删除缓存中的角色
			redisUtil.deleteKey(GlobalConstant.AUTHORIZATION_CACHE_NAME+":"+userId);

			//先删除用户角色，再进行批量新增
			orgUserRoleMapper.deleteByUserid(userId);

			if (roles!=null && !roles.equals("")) {
				String[] roleArr = roles.split(",");
				for (int i = 0; i < roleArr.length; i++) {
					OrgUserRole userRole = new OrgUserRole();
					long roleId = Long.parseLong(roleArr[i]);
					OrgRole role = new OrgRole();
					role.setId(roleId);
					userRole.setOrgRole(role);
					userRole.setUserid(userId);
					orgUserRoleMapper.insert(userRole);
				}
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

	/**
	 * 获取用户角色IDs字符串
	 * @param userid
	 * @return
	 */
	@Override
	public String findRoleIdsByUserId(String userid) {
		StringBuilder roleIds = new StringBuilder();
		List<OrgUserRole> list = orgUserRoleMapper.selectByUserId(userid);
		for (int i = 0; i < list.size(); i++) {
			if (i>0){
				roleIds.append(",");
			}
			roleIds.append(list.get(i).getOrgRole().getId());
		}
		return roleIds.toString();
	}

	@Override
	public void deleteByUserId(String userid) {
		orgUserRoleMapper.deleteByUserid(userid);
	}
}
