package com.corpdata.system.dic.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import com.corpdata.system.dic.entity.SysDicType;
import com.github.pagehelper.Page;

@Mapper
public interface SysDicTypeMapper{
	
    int deleteByPrimaryKey(String id);

    int insert(SysDicType record);

    SysDicType selectByPrimaryKey(String id);

    int updateByPrimaryKey(SysDicType record);
    
    Page<SysDicType> selectAll();
    
    List<Map<String,Object>> selectAllByCombox();
    
    List<SysDicType> selectAllByCondition(Map<String, Object> params);
    
}