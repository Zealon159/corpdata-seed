package com.cpda.system.org.service.impl;

import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.common.utils.PageConvertUtil;
import com.cpda.system.org.dao.OrgRoleMapper;
import com.cpda.system.org.dao.OrgRolePermissionMapper;
import com.cpda.system.org.entity.OrgRole;
import com.cpda.system.org.service.OrgRoleService;
import com.cpda.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 角色服务类
 * @author zealon
 * @date 2018年3月1日
 */
@Transactional
@Service
public class OrgRoleServiceImpl extends AbstractBaseService<OrgRole> implements OrgRoleService {

	Logger logger = LoggerFactory.getLogger(OrgRoleServiceImpl.class);

	@Autowired
	private OrgRoleMapper orgRoleMapper;

	@Autowired
	private OrgRolePermissionMapper rolePermissionMapper;

	/**
	 * 获取用户角色列表
	 * @param userid
	 * @return
	 */
	public List<String> getRolesByUser(String userid){
		return orgRoleMapper.getRolesByUser(userid);
	}

	/**
	 * 获取角色json data
	 * @return
	 */
	@Override
	public List<Map<String, String>> selectData() {
		return orgRoleMapper.selectData();
	}

	/**
	 * 关键字分页查询角色
	 * @param page
	 * @param rows
	 * @param keyword
	 * @return
	 */
	@Override
	public String findByKeyword(int page, int rows, String keyword) {
		PageHelper.startPage(page, rows);
		Page<OrgRole> list = (Page<OrgRole>) orgRoleMapper.selectByKeyword(keyword);
		return PageConvertUtil.getGridJson(list);
	}

	@Override
	public Result save(OrgRole record) {
		logger.info("{} 新增角色，名称:{}",UserUtil.getCurrentUserid(),record.getRoleName());
		return super.save(record);
	}

	@Override
	public Result deleteById(Long id) {

		// 删除角色权限
		rolePermissionMapper.deleteByRoleId(id);

		logger.info("{} 删除角色，id:{}",UserUtil.getCurrentUserid(),id);

		// 删除角色
		return super.deleteById(id);
	}
}
