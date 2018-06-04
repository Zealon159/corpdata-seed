package com.corpdata.app.test.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.core.base.AbstractService;

import tk.mybatis.mapper.entity.Condition;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements TestService {

	@Override
	public Test findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
