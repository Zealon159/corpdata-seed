package com.corpdata.system.org.service;

public interface OrgDeptService {
	
	/**
	 * 获取下拉json数据
	 * @return
	 */
	String findByCombox(boolean hasRoot);
	/**
	 * 计算当前组织层级id
	 * @param parentFolderid
	 * @return
	 */
	String calculateFolderid(String parentFolderid);
}
