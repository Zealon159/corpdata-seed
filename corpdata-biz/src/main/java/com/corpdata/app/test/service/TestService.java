package com.corpdata.app.test.service;

import com.corpdata.app.test.entity.Test;
import com.corpdata.core.base.Service;
import com.corpdata.core.result.Result;

public interface TestService extends Service<Test>{
	
	Result deleteByTotal(int total);
}
