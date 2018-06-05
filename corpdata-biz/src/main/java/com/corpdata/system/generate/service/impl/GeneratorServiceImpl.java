package com.corpdata.system.generate.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.DataGrid;
import com.corpdata.system.generate.dao.GeneratorMapper;
import com.corpdata.system.generate.service.GeneratorService;
import com.corpdata.system.generate.util.GenUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class GeneratorServiceImpl implements GeneratorService {
	
	@Autowired
	GeneratorMapper generatorMapper;

	@Override
	public byte[] generatorCode(String[] tableNames,String packageName,String packageName2) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for(String tableName : tableNames){
			
			//查询表信息
			Map<String, Object> table = generatorMapper.get(tableName);
			
			//查询列信息
			List<Map<String, Object>> columns = generatorMapper.listColumns(tableName);
			
			//生成代码
			GenUtils.generatorCode(table, columns, zip,packageName, packageName2);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
	
	/**
	 * 查询数据列表
	 * 
	 * @param dt
	 * @return
	 */
	public String findByPage(DataGridRequestDTO dgRequest) {
		PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());
		Page<Map<String,Object>> list = generatorMapper.list();
		return PageConvertUtil.getGridJson(list);
	}
}
