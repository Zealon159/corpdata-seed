package com.cpda.system.generate.dao;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface SqlServerGeneratorMapper {
	
	@Select("select name tableName,crdate createTime from sysobjects where xtype='u'")
	Page<Map<String, Object>> list();

	@Select("select count(*) from sysobjects where xtype='u'")
	int count(Map<String, Object> map);

	@Select("select name tableName,crdate createTime from sysobjects where xtype='u' and name = #{tableName}")
	Map<String, Object> get(@Param("tableName") String tableName);

	@Select("SELECT  c.TABLE_NAME tableName,  c.COLUMN_NAME columnName,  c.DATA_TYPE dataType,  "+
			" c.CHARACTER_MAXIMUM_LENGTH dataTypeLength,  (SELECT name FROM syscolumns WHERE id=Object_Id(#{tableName}) and colid IN"+
			"(SELECT keyno from sysindexkeys WHERE id=Object_Id(#{tableName})) ) primaryKey FROM [INFORMATION_SCHEMA].[COLUMNS] c "+
			"WHERE TABLE_NAME = #{tableName} ")
	List<Map<String, Object>> listColumns(@Param("tableName") String tableName);
	
}

