package com.corpdata.system.dic.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.system.dic.dao.SysDicTypeMapper;
import com.corpdata.system.dic.entity.SysDicType;
import com.corpdata.system.dic.service.SysDicTypeService;

@Service("sysDicTypeService")
public class SysDicTypeServiceImpl extends AbstractBaseService<SysDicType> implements SysDicTypeService {
	
	@Autowired
	private SysDicTypeMapper sysDicTypeMapper;

	/**
	 * 获取下拉json数据
	 * @return
	 */
	public String findByCombox(){
		String json = CorpdataUtil.getComboxJson(sysDicTypeMapper.selectAllByCombox());
		return json;
	}
	
	public List<SysDicType> selectAllByCondition(String key, Object value){
		Map<String, Object> params = new HashMap<String, Object>();
		if(CorpdataUtil.isNotBlank(key)){
			params.put(key, value);
		}
		return sysDicTypeMapper.selectAllByCondition(params);	
	}
	
	public Map<String, Object> getSysDicTypeNav(){
		Map<String, Object> group = new HashMap<String, Object>();
		List<SysDicType> projectList = selectAllByCondition("groupId", "project");
		List<SysDicType> customerList = selectAllByCondition("groupId", "customer");
		List<SysDicType> contractList = selectAllByCondition("groupId", "contract");
		group.put("code", 1);
		group.put("project", projectList);
		group.put("customer", customerList);
		group.put("contract", contractList);
		group.put("desc", "success");
		return group;
	}
	
}
