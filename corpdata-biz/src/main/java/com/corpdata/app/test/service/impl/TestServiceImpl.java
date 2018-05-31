package com.corpdata.app.test.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.app.test.dao.TestMapper;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractService;

import tk.mybatis.mapper.common.condition.SelectByConditionMapper;
import tk.mybatis.mapper.entity.Condition;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements TestService {
	
	@Resource
    private TestMapper testMapper;

	
//	@Override
//	public void save(Test model) {
//		String id =  CorpdataUtil.getUUID();
//		model.setId(id);
//		System.out.println(model.getId()+"--->"+id);
//		super.save(model);
//	}
	 
	
	@Override
	public String findByPage(DataGridRequestDTO dgRequest) {
		// TODO Auto-generated method stub
		return super.findByPage(dgRequest);
	}
	
}
