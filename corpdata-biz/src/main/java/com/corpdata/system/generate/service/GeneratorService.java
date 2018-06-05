package com.corpdata.system.generate.service;

import org.springframework.stereotype.Service;
import com.corpdata.common.domain.DataGridRequestDTO;

@Service
public interface GeneratorService {

	//生成基础代码
	byte[] generatorCode(String[] tableNames,String packageName,String packageName2);
	
	String findByPage(DataGridRequestDTO dgRequest);
	
}