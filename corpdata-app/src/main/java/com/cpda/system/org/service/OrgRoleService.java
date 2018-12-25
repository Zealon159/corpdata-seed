package com.cpda.system.org.service;

import com.cpda.common.base.BaseService;
import com.cpda.system.org.entity.OrgRole;

import java.util.List;
import java.util.Map;

/**
 * 用户角色
 */
public interface OrgRoleService extends BaseService<OrgRole> {

	List<String> getRolesByUser(String userid);

	/**
	 * 为select提供数据
	 * @return
	 */
	List<Map<String, String>> selectData();

	/**
	 * 关键字分页查询角色
	 * @param page
	 * @param rows
	 * @param keyword
	 * @return
	 */
	String findByKeyword(int page, int rows, String keyword);
}
