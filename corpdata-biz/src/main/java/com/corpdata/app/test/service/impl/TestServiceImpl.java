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

	public Result deleteByTotal(int total){
		if(testMapper.deleteByTotal(total)>0){
			return ResultUtil.success();
		}else{
			return ResultUtil.fail();
		}
	}
	
	@Override
	public void save(Test model) {
		String id =  CorpdataUtil.getUUID();
		model.setId(id);
		System.out.println(model.getId()+"--->"+id);
		super.save(model);
	}
	
	@Override
	public List<Test> findByCondition(Condition condition) {
		// TODO Auto-generated method stub
		//condition.
		
		return super.findByCondition(condition);
	}
	
	public List<Test> findBy(Map<String,Object> params){
		Condition c = new Condition(Test.class);
		Condition.Criteria criteria = c.createCriteria();
		//criteria.andCondition("title", t.getTitle());
		///criteria.andEqualTo("title", params.get("aaa"));
		return testMapper.selectByCondition(c);
	}
	
}
