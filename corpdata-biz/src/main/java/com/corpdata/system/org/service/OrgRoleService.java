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
import com.corpdata.system.org.entity.OrgPermission;
import com.corpdata.system.org.entity.OrgRole;
import com.corpdata.system.org.entity.OrgRolePermission;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.api.redis.RedisService;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 角色服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Transactional
@Service
public class OrgRoleService {
	
	@Autowired
	private OrgRoleMapper orgRoleMapper;
	
	@Autowired
	private OrgRolePermissionMapper orgRolePermissionMapper;
	
	@Autowired
	RedisService redisService;
	
	public Result insert(OrgRole record) {
		record.setId(CorpdataUtil.getUUID());
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		if (orgRoleMapper.insert(record)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result update(OrgRole record) {
		record.setModified(new Date());
		if (orgRoleMapper.updateByPrimaryKey(record)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public Result delete(String id) {
		if (orgRoleMapper.deleteByPrimaryKey(id)>0) {
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	public String findByPage(int pageNo, int pageSize, String searchStr) {
		PageHelper.startPage(pageNo, pageSize);
		Map<String, Object> params = new HashMap<String, Object>();
		if(CorpdataUtil.isNotBlank(searchStr)){
			params.put("searchStr", searchStr);
		}
		Page<OrgRole> list = orgRoleMapper.selectAll(params);
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
