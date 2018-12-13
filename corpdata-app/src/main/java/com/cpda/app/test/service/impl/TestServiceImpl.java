package com.cpda.app.test.service.impl;

import com.cpda.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cpda.common.base.AbstractBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cpda.app.test.dao.TestMapper;
import com.cpda.app.test.entity.Test;
import com.cpda.app.test.service.TestService;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-12-13 15:39:31
 */
@Service
public class TestServiceImpl extends AbstractBaseService<Test> implements TestService {

    protected final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public Result update(Test record) {
        logger.info("update Test:{}",record.getId());
        return super.update(record);
    }

    @Override
    public Result save(Test record) {
        logger.info("add Test:{}",record.getId());
        return super.save(record);
    }

    @Override
    public Result deleteById(Long id) {
        logger.info("delete Test:{}",id);
        return super.deleteById(id);
    }
}
