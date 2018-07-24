package com.corpdata.app.test.service;

import com.corpdata.app.test.entity.TbAccount;
import com.corpdata.core.base.BaseService;

/**
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-07-23 11:28:57
 * 
 */
public interface TbAccountService extends BaseService<TbAccount>{
	void buyCar(String uid,String carName,int number);
}
