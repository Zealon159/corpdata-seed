package com.corpdata.app.test.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.app.test.dao.TestMapper;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.AbstractService;
import com.corpdata.core.result.Result;
import com.corpdata.core.result.ResultGenerator;
import tk.mybatis.mapper.entity.Condition;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements TestService {
	
	@Resource
    private TestMapper testMapper;

	public Result deleteByTotal(int total){
		if(testMapper.deleteByTotal(total)>0){
			return ResultGenerator.genSuccessResult();
		}else{
			return ResultGenerator.genFailResult("删除失败!");
		}
	}
	
	@Override
	public void save(Test model) {
		String id =  CorpdataUtil.getUUID();
		model.setId(id);
		System.out.println(model.getId()+"--->"+id);
		super.save(model);
	}
}
