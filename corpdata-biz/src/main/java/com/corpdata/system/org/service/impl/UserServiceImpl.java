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
import com.corpdata.system.org.entity.OrgRole;
import com.corpdata.system.org.entity.OrgUser;
import com.corpdata.system.org.service.UserService;
import com.corpdata.system.security.shiro.util.ShiroUserPwdUtil;
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
 * 用户服务
 * @author zealon
 * @date 2018年3月1日
 */
@Service("userService")
public class UserServiceImpl extends AbstractService<OrgUser> implements UserService {

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
		//System.out.println(list.toString());
		return set;
	}

	public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		if(dgRequest.getParams()!=null && dgRequest.getParams().get("deptId")!=null){
			if(dgRequest.getParams().get("deptId").equals("root")){
				dgRequest.getParams().put("deptId", null);
			}
		}
		Page<OrgUser> list = orgUserMapper.selectAll(dgRequest.getParams());
		//Page<OrgUser> list = orgUserMapper.selectAllByKeyword(keyword, deptId);
		return PageConvertUtil.getGridJson(list);
	}
	
//	public String memberFindByPage(int pageNo, int pageSize,String keyword, String deptId, List<ProjectMember> memList) {
//		PageHelper.startPage(pageNo, pageSize);
//		
//		if(("root").equals(deptId) || ("").equals(deptId)){
//			deptId = null;
//		}
//		if(("").equals(keyword)){
//			keyword = null;
//		}
//		Page<OrgUser> list = orgUserMapper.selectAllByKeyword(keyword, deptId);
//		return getJson(memList,list);
//	}
//	public String getJson(List<ProjectMember> memList,Page<OrgUser> list){
//		String json="{\"code\":\"0\",\"msg\":\"\",\"count\":\""+list.getTotal()+"\",\"data\":[";
//		String menName="";
//		for(ProjectMember mem:memList){
//			menName=menName+","+mem.getUser().getUserid();
//		}
//		int i=0;
//		for(OrgUser ou:list){
//			if(i>0){json=json+",";}
//			json=json+"{";
//			if(menName.contains(ou.getUserid())){
//				json=json+"\"LAY_CHECKED\":\"true\",";
//			}
//			json=json+"\"id\":\""+ou.getId()+"\",\"orgDept\":{\"foldername\":\""+ou.getOrgDept().getFoldername()+"\",\"id\":\""+ou.getOrgDept().getId()+"\"},\"sortNumber\":\""+ou.getSortNumber()+"\",\"userName\":\""+ou.getUserName()+"\",\"userid\":\""+ou.getUserid()+"\"}";
//			i++;
//		}
//		json=json+"]}";
//		return json;
//	}
//	
//	
	/***
	 * 根据用户id获取用户信息
	 */
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
	
	public Result update(OrgUser record,String orgDeptId){
		record.setModified(new Date());
		if(null != orgDeptId && !("").equals(orgDeptId)){
			record.setOrgDept(new OrgDept(orgDeptId));
		}
		return super.update(record);
	}
	
	public Result delete(String id){
		Result r = ResultUtil.fail();
		if(id.equals("304a3837c36911e7886e4ccc6a41b42a")){
			r = ResultUtil.fail("不能删除系统管理员哦！");
		}else{
			if(orgUserMapper.deleteByPrimaryKey(id)>0){
				r = ResultUtil.success();
			}
		}
		return r;
	}
	
	public OrgUser selectByUserId(String UserId){
		return orgUserMapper.selectByUserId(UserId);
	}
	
	/**
	 * 修改用户密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	public Result modPassword(String id, String newPassword) {
		OrgUser user = super.findById(id);
		String password = ShiroUserPwdUtil.generateEncryptPwd(user.getUserid(), newPassword);//密码加密
		user.setUserPwd(password);
		return super.update(user);
	}

}
