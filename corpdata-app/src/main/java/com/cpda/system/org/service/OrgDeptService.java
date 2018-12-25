package com.cpda.system.org.service;

import com.cpda.common.base.BaseService;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgDept;

public interface OrgDeptService extends BaseService<OrgDept> {
	
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

	Result update(OrgDept record, String oldParentFolderid);
	
	String findByFromJsonTree(String id);
	
	String findByFolderid(String folderid);
}
