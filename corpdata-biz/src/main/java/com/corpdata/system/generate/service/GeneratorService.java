package com.corpdata.system.generate.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.corpdata.common.result.DataGrid;

@Service
public interface GeneratorService {

	//生成基础代码
	byte[] generatorCode(String[] tableNames,String packageName,String packageName2);
	
	public DataGrid<Map<String,Object>> selectDataGrid(DataGrid dt);
	
}