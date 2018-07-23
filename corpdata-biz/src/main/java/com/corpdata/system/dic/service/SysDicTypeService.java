package com.corpdata.system.dic.service;

import java.util.List;
import java.util.Map;

import com.corpdata.core.base.BaseService;
import com.corpdata.system.dic.entity.SysDicType;

public interface SysDicTypeService extends BaseService<SysDicType> {
	/**
	 * 获取下拉json数据
	 * @return
	 */
	String findByCombox();
	
	List<SysDicType> selectAllByCondition(String key, Object value);
	
	Map<String, Object> getSysDicTypeNav();
}
