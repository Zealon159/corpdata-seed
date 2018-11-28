package com.cpda.system.dic.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.dic.entity.SysDataDic;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

@Mapper
public interface DataDicMapper extends BaseMapper<SysDataDic> {

    Page<SysDataDic> selectAllByCondition(@Param("dictype") String dictype, @Param("searchStr") String searchStr);

    List<Map<String, Object>> selectAllByCombox();

    List<Map<String, Object>> selectAllByComboxByCode(String code);

    List<SysDataDic> findAllCode(String typeid);
}