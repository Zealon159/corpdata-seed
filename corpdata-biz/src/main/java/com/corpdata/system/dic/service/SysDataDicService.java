package com.corpdata.system.dic.service;

import java.util.List;
import com.corpdata.common.result.Result;
import com.corpdata.system.dic.entity.SysDataDic;

public interface SysDataDicService {
	
	Result delete(String id,String dicType);
	
	/**
	 * @desc: 根据id查找字典名称
	 * @param id
	 * @return
	 */
	String getDataDicText(String id);
	
	/**
	 * @desc: 获取下拉json数据
	 * @return
	 */
	String findByCombox();
	
	/**
	 * @desc: 根据类型，获取下拉json数据
	 * @return
	 */
	String findByComboxByCode(String code);
	
	List<SysDataDic> findAllCode(String typeid);
}
