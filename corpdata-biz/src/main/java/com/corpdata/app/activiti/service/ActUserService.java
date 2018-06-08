package com.corpdata.app.activiti.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.corpdata.app.activiti.dao.ActUserMapper;
import com.corpdata.core.datasource.DataSourceEnum;
import com.corpdata.core.datasource.aop.DynamicSwitchDataSource;

@Service
public class ActUserService {
	
	@Autowired
	private ActUserMapper actUserMapper;
	
	public String getAll(){
		List<Map<String,Object>> list = actUserMapper.getAll();
		return JSON.toJSONString(list);
	}
	
	@DynamicSwitchDataSource(dataSource = DataSourceEnum.SQLSERVER)
	public String getBPMAll(){
		List<Map<String,Object>> list = actUserMapper.getBPMAll();
		return JSON.toJSONString(list);
	}
	
}
