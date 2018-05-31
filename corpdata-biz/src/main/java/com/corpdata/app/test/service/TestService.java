package com.corpdata.app.test.service;

import java.util.List;
import java.util.Map;

import com.corpdata.app.test.entity.Test;
import com.corpdata.common.result.Result;
import com.corpdata.core.base.Service;

public interface TestService extends Service<Test>{
	
	Result deleteByTotal(int total);
	
	List<Test> findBy(Map<String,Object> params);
}
