package com.corpdata.app.test.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.core.base.AbstractService;

@Service
@Transactional
public class TestServiceImpl extends AbstractService<Test> implements TestService {
	
}
