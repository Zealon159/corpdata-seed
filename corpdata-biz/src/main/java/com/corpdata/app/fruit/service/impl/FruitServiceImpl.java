package com.corpdata.app.fruit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.corpdata.core.base.AbstractBaseService;
import com.corpdata.app.fruit.dao.FruitMapper;
import com.corpdata.app.fruit.entity.Fruit;
import com.corpdata.app.fruit.service.FruitService;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-06-05 22:20:22
 */
@Service
@Transactional
public class FruitServiceImpl extends AbstractBaseService<Fruit> implements FruitService {
	
}
