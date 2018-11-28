package com.cpda.system.generate.service.impl;

import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.utils.PageConvertUtil;
import com.cpda.core.datasource.DataSourceContextHolder;
import com.cpda.core.datasource.DataSourceEnum;
import com.cpda.system.generate.dao.GeneratorMapper;
import com.cpda.system.generate.dao.SqlServerGeneratorMapper;
import com.cpda.system.generate.service.GeneratorService;
import com.cpda.system.generate.util.GenUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

@Service
public class GeneratorServiceImpl implements GeneratorService {
	
	@Autowired
	GeneratorMapper generatorMapper;

	@Autowired
	SqlServerGeneratorMapper sqlServerGeneratorMapper;
	
	@Override
	public byte[] generatorCode(String[] tableNames,String packageName,String packageName2,
			String subject,String dbType,String dbEnum) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		
		//切换至当前数据源
		DataSourceContextHolder.setTargetDataSource(getCurrentByDatasource(dbEnum)); 
		try{
			for(String tableName : tableNames){

				//查询表信息
				Map<String, Object> table = null;
				if(dbType.equals("mysql")){
					table=generatorMapper.get(tableName);
				}else if(dbType.equals("sqlserver")){
					table=sqlServerGeneratorMapper.get(tableName);
				}

				//查询列信息
				List<Map<String, Object>> columns = null;
				if(dbType.equals("mysql")){
					columns=generatorMapper.listColumns(tableName);
				}else if(dbType.equals("sqlserver")){
					columns=sqlServerGeneratorMapper.listColumns(tableName);
				}

				//生成代码
				GenUtils.generatorCode(table, columns, zip,packageName, packageName2,subject);
			}
		}finally {
			DataSourceContextHolder.resetDefaultDataSource();
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}
	
	/**
	 * 查询数据列表
	 * @param dgRequest
	 * @return
	 */
	public String findByPage(DataGridRequestDTO dgRequest) {
		String dbType = "";
		String keyword = null;
		String dbEnum = "";
		Page<Map<String,Object>> list = null;

		if(dgRequest.getParams()!=null){
			dbType = dgRequest.getParams().get("dbType").toString();
			keyword = dgRequest.getParams().get("keyword").toString();
			dbEnum = dgRequest.getParams().get("dbEnum").toString();
		}
		
		//切换至当前数据源
		DataSourceContextHolder.setTargetDataSource(getCurrentByDatasource(dbEnum)); 
		try{
			PageHelper.startPage(dgRequest.getPage(), dgRequest.getLimit());


			if(dbType.equals("mysql")){
				list = generatorMapper.list();
			}else if(dbType.equals("sqlserver")){
				list = sqlServerGeneratorMapper.list();
			}
		}finally {
			// 必须调用移除，不然导致默认数据源获取不准确
			DataSourceContextHolder.resetDefaultDataSource();
		}

		return PageConvertUtil.getGridJson(list);
	}
	
	/**
	 * 获取当前枚举值
	 * @param dbEnum
	 * @return
	 */
	public DataSourceEnum getCurrentByDatasource(String dbEnum){
		DataSourceEnum currentDs = DataSourceEnum.MASTER;
		for(DataSourceEnum ds : DataSourceEnum.values()){
			if(ds.toString().equals(dbEnum)){
				currentDs = ds;
				break;
			}
		}
		return currentDs;
	}
}
