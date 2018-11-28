package com.cpda.system.generate.service;

import com.cpda.common.domain.DataGridRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface GeneratorService {

	//生成基础代码
	byte[] generatorCode(String[] tableNames, String packageName, String packageName2, String subject, String dbType, String dbEnum);
	
	String findByPage(DataGridRequestDTO dgRequest);
	
}