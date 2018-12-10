package com.cpda.system.org.service.impl;

import com.cpda.common.api.RedisService;
import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.common.utils.PageConvertUtil;
import com.cpda.system.org.dao.OrgUserMapper;
import com.cpda.system.org.entity.OrgDept;
import com.cpda.system.org.entity.OrgUser;
import com.cpda.system.org.service.OrgUserService;
import com.cpda.system.security.shiro.util.ShiroUserPwdUtil;
import com.cpda.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户服务
 * @author zealon
 * @date 2018年3月1日
 */
@Service("userService")
public class OrgUserServiceImpl extends AbstractBaseService<OrgUser> implements OrgUserService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Autowired
	private OrgUserMapper orgUserMapper;
	
	/*@Autowired
	private OrgRoleMapper orgRoleMapper;

	@Autowired
	private OrgPermissionMapper orgPermissionMapper;*/
	
	@Autowired
	RedisService redisService;
	
	/*@Autowired
	private OrgUserRoleService orgUserRoleService;*/
	
	/**
	 * 获取当前用户所有角色
	 */
	public Set<String> getRolesByUser(String userId) {
		//List<String> list =orgRoleMapper.getRolesByUser(userId);
		List<String> list = new ArrayList<>();
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
		//List<String> list =orgPermissionMapper.getPermissionsByUser(userId);
		List<String> list = new ArrayList<>();
		Set<String> set = new HashSet<String>();
		for(String str : list){
			set.add(str);
		}
		logger.debug("查询数据库:"+list.toString());
		return set;
	}

	@Override
	public String findByPage(int page,int rows,Long deptId) {
		if(deptId==-1){
			deptId = null;
		}
		PageHelper.startPage(page, rows);
		Page<OrgUser> list = (Page<OrgUser>) orgUserMapper.selectAll(deptId);
		return PageConvertUtil.getGridJson(list);
	}
	
	/***
	 * 根据用户id获取用户信息
	 */
	public OrgUser getUserInfoByUserid(String userId){
		OrgUser user = orgUserMapper.selectByUserId(userId);
		return user;
	}
	/**
	 * 根据id获取下级员工列表
	 */
	public List<OrgUser> selectByIds(String ids){
		List<OrgUser> ouList=orgUserMapper.selectByIds(ids);
		return ouList;
	}
	
	public Result insert(OrgUser record, Long orgDeptId, String deptids, String roleProject) {
		
		Date date = new Date();
		record.setCreater(UserUtil.getCurrentUserid());
		record.setCreated(date);
		record.setModified(date);
		record.setEnabledState(true);
		record.setOrgDept(new OrgDept(orgDeptId));
		String newPwd = ShiroUserPwdUtil.generateEncryptPwd(record.getUserid(), record.getUserPwd());
		record.setUserPwd(newPwd);
		/*if(deptids!=null){
			userDeptService.insert(record.getUserid(), deptids);
		}*/
		/*//添加员工分店权限
		JSONArray reList = JSONArray.parseArray(roleProject.replaceAll("&quot;", "\""));
		for(int i = 0;i<reList.size();i++){
			JSONObject re=reList.getJSONObject(i);
			if(re.getInteger("teamRole") == 1){
				projectTeamServ.insert(re.getString("projectid"),record.getId(), "1", 1);
			}else{
				projectTeamServ.insert(re.getString("projectid"),record.getId(), "2", 0);
			}
			
		}
		//对用户赋予角色
		if(!(record.getRoleid()==null||record.getRoleid().equals(""))) {
			orgUserRoleService.createUserRoles(record.getUserid(),"[\""+record.getRoleid()+"\"]");
		}*/
		
		return super.save(record);
	}

	@Override
	public Result deleteById(Long id){
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
	public Result modPassword(Long id, String newPassword) {
		OrgUser user = findById(id);
		String password = ShiroUserPwdUtil.generateEncryptPwd(user.getUserid(), newPassword);//密码加密
		user.setUserPwd(password);
		return super.update(user);
	}
	
	public Result update(OrgUser record,Long orgDeptId,String deptids,String sysAttachmentPortraitId,String roleProject){
		record.setModified(new Date());
		/*if(deptids!=null){
			userDeptService.insert(record.getUserid(), deptids);
		}
		SysAttachment sa=new SysAttachment();
		sa.setId(sysAttachmentPortraitId);
		record.setSysAttachmentPortrait(sa);*/


		return super.update(record);
	}
	/**
	 * 获取下拉json数据
	 * @return
	 */
	public String findByCombox(){
		//String json = CorpdataUtil.getComboxJson(orgUserMapper.selectAllByCombox());
		String json = "";
		return json;
	}
}
