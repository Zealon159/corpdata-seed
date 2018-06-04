package com.corpdata.system.org.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.system.org.dao.OrgRoleMapper;
import com.corpdata.system.org.dao.OrgRolePermissionMapper;
import com.corpdata.system.org.entity.OrgDept;
import com.corpdata.system.org.entity.OrgPermission;
import com.corpdata.system.org.entity.OrgRole;
import com.corpdata.system.org.entity.OrgRolePermission;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.api.redis.RedisService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 角色服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Transactional
@Service
public class OrgRoleService extends AbstractService<OrgRole> {
	
	@Autowired
	private OrgRoleMapper orgRoleMapper;
	
	@Autowired
	private OrgRolePermissionMapper orgRolePermissionMapper;
	
	@Autowired
	RedisService redisService;
	
	@Override
	public Result save(OrgRole record) {
		// TODO Auto-generated method stub
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		return super.save(record);
	}
	
	@Override
	public Result update(OrgRole record) {
		// TODO Auto-generated method stub
		record.setModified(new Date());
		return super.update(record);
	}
	
	@Override
	public OrgRole findById(String id) {
		// TODO Auto-generated method stub
		return orgRoleMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		Page<OrgRole> list =  (Page<OrgRole>) orgRoleMapper.selectAll(dgRequest.getParams());
		return PageConvertUtil.getGridJson(list);
	}
	
	/**
	 * 保存角色权限
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	public Result saveRolePermission(String roleId,String permissionIds) {
		Result r = ResultUtil.fail();
		OrgRole role=new OrgRole();
		role.setId(roleId);
		try {
			//删除缓存
			redisService.delete("authorizationCache:"+UserUtil.getCurrentUserid());
			//删除当前角色所有权限
			orgRolePermissionMapper.deleteByRoleId(roleId);
			//新增已选权限
			OrgRolePermission orp =null;
			OrgPermission op=null;
			if(permissionIds!=null && !"".equals(permissionIds)){
				String [] permissionArr=permissionIds.split(",");
				for(String permissionId:permissionArr){
					op=new OrgPermission();
					op.setId(permissionId);
					orp=new OrgRolePermission();
					orp.setId(CorpdataUtil.getUUID());
					orp.setRole(role);
					orp.setPermission(op);
					orgRolePermissionMapper.insert(orp);
				}
			}
			r = ResultUtil.success();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r = ResultUtil.error();
		}
		return r;
	}
	
	public OrgRole selectByPrimaryKey(String id){
		return orgRoleMapper.selectByPrimaryKey(id);
	}
	
	public List<OrgRole> findOrgRoleList(){
		return orgRoleMapper.findOrgRoleList(null);
	}
	
	public List<String> getRolesByUser(String userid){
		return orgRoleMapper.getRolesByUser(userid);
	}
}
