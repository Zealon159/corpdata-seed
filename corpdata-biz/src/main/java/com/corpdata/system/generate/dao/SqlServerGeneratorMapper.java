package com.corpdata.system.generate.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.github.pagehelper.Page;

@Mapper
public interface SqlServerGeneratorMapper {
	
	@Select("select name tableName,crdate createTime from sysobjects where xtype='u'")
	Page<Map<String, Object>> list();

	@Select("select count(*) from sysobjects where xtype='u'")
	int count(Map<String, Object> map);

	@Select("select name tableName,crdate createTime from sysobjects where xtype='u' and name = #{tableName}")
	Map<String, Object> get(@Param("tableName") String tableName);

	@Select("select a.name tableName,b.name columnName,c.name dataType,c.length dataTypeLength from sysobjects a,syscolumns b,systypes c where a.id=b.id"
			+"and a.name=#{tableName} and a.xtype='U' and b.xtype=c.xtype")
	List<Map<String, Object>> listColumns(@Param("tableName") String tableName);
	
}

