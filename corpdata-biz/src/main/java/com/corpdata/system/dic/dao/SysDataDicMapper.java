package com.corpdata.system.dic.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.dic.entity.SysDataDic;
import com.github.pagehelper.Page;

public interface SysDataDicMapper extends BaseMapper<SysDataDic>{
	
    Page<SysDataDic> selectAllByCondition(@Param("dictype") String dictype, @Param("searchStr") String searchStr);
    
    List<Map<String,Object>> selectAllByCombox();

	List<Map<String, Object>> selectAllByComboxByCode(String code);
	
	List<SysDataDic> findAllCode(String typeid);
}