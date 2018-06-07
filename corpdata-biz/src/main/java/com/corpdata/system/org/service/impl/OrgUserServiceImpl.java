package com.corpdata.system.org.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corpdata.system.org.dao.OrgPermissionMapper;
import com.corpdata.system.org.dao.OrgRoleMapper;
import com.corpdata.system.org.dao.OrgUserMapper;
import com.corpdata.system.org.entity.OrgDept;
import com.corpdata.system.org.entity.OrgUser;
import com.corpdata.system.org.service.OrgUserService;
import com.corpdata.system.security.shiro.util.ShiroUserPwdUtil;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.corpdata.common.api.redis.RedisService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractBaseService;

/**
 * 用户服务
 * @author zealon
 * @date 2018年3月1日
 */
@Service("userService")
public class OrgUserServiceImpl extends AbstractBaseService<OrgUser> implements OrgUserService{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	private OrgUserMapper orgUserMapper;
	
	@Autowired
	private OrgRoleMapper orgRoleMapper;

	@Autowired
	private OrgPermissionMapper orgPermissionMapper;
	
	@Autowired
	RedisService redisService;
	

	/**
	 * 获取当前用户所有角色
	 */
	public Set<String> getRolesByUser(String userId) {
		List<String> list =orgRoleMapper.getRolesByUser(userId);
		Set<String> set = new HashSet<String>();
		for(String str : list){
			set.add(str);
		}
		return set;
	}

	/**
	 * 获取当前用户所有权限
	 */
	public Set<String> getPermissionsByUser(String userId) {
		List<String> list =orgPermissionMapper.getPermissionsByUser(userId);
		Set<String> set = new HashSet<String>();
		for(String str : list){
			set.add(str);
		}
		logger.debug("查询数据库:"+list.toString());
		return set;
	}

	@Override
	public String findByPage(DataGridRequestDTO dgRequest) {
		if(dgRequest.getParams()!=null && dgRequest.getParams().get("deptId")!=null){
			if(dgRequest.getParams().get("deptId").equals("root")){
				dgRequest.getParams().put("deptId", null);
			}
		}
		return super.findByPage(dgRequest);
	}
	
	/***
	 * 根据用户id获取用户信息
	 */
	//@DynamicSwitchDataSource(dataSource = "ds_master")
	public OrgUser getUserInfoByUserid(String userId){
		OrgUser user = orgUserMapper.getUserInfoByUserid(userId);
		return user;
	}
	
	public Result insert(OrgUser record,String orgDeptId) {
		Date date = new Date();
		record.setId(CorpdataUtil.getUUID());
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		record.setEnabledState(true);
		record.setOrgDept(new OrgDept(orgDeptId));
		String newPwd = ShiroUserPwdUtil.generateEncryptPwd(record.getUserid(), record.getUserPwd());
		record.setUserPwd(newPwd);
		return super.save(record);
	}

	@Override
	public Result deleteById(String id){
		Result r = ResultUtil.fail();
		if(id.equals("304a3837c36911e7886e4ccc6a41b42a")){
			r = ResultUtil.fail("不能删除系统管理员哦！");
		}else{
			if(orgUserMapper.deleteById(id)>0){
				r = ResultUtil.success();
			}
		}
		return r;
	}
	
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	public Result modPassword(String id, String newPassword) {
		OrgUser user = findById(id);
		String password = ShiroUserPwdUtil.generateEncryptPwd(user.getUserid(), newPassword);//密码加密
		user.setUserPwd(password);
		return super.update(user);
	}
	
	public Result update(OrgUser record,String orgDeptId){
		record.setModified(new Date());
		if(null != orgDeptId && !("").equals(orgDeptId)){
			record.setOrgDept(new OrgDept(orgDeptId));
		}
		return super.update(record);
	}

}
