package com.corpdata.system.org.service;

import com.corpdata.common.result.Result;
import com.corpdata.core.base.BaseService;
import com.corpdata.system.org.entity.OrgDept;

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
}
