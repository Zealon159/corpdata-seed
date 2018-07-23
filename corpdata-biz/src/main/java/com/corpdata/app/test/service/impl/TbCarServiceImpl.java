package com.corpdata.app.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.app.test.dao.TbCarMapper;
import com.corpdata.app.test.entity.TbCar;
import com.corpdata.app.test.service.TbCarService;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-07-23 11:28:57
 */
@Service
@Transactional
public class TbCarServiceImpl extends AbstractBaseService<TbCar> implements TbCarService {
	
}
