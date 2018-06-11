package com.corpdata.system.error.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.system.error.dao.SysHttpErrorMapper;
import com.corpdata.system.error.entity.SysHttpError;
import com.corpdata.system.error.service.SysHttpErrorService;

/**
 * 系统HTTP请求异常
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-06-11 09:32:48
 */
@Service
@Transactional
public class SysHttpErrorServiceImpl extends AbstractBaseService<SysHttpError> implements SysHttpErrorService {
	
}
