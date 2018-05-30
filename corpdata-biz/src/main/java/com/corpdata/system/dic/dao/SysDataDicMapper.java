package com.corpdata.system.dic.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.corpdata.system.dic.entity.SysDataDic;
import com.github.pagehelper.Page;

@Mapper
public interface SysDataDicMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDataDic record);

    SysDataDic selectByPrimaryKey(String id);

    Page<SysDataDic> selectAllByCondition(@Param("dictype") String dictype, @Param("searchStr") String searchStr);

    int updateByPrimaryKey(SysDataDic record);
    
    List<Map<String,Object>> selectAllByCombox();

	List<Map<String, Object>> selectAllByComboxByCode(String code);
	
	List<SysDataDic> findAllCode(String typeid);
}