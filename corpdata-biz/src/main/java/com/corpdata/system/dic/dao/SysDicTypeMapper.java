package com.corpdata.system.dic.dao;

import java.util.List;
import java.util.Map;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.dic.entity.SysDicType;

public interface SysDicTypeMapper extends BaseMapper<SysDicType>{

    List<Map<String,Object>> selectAllByCombox();
    
    List<SysDicType> selectAllByCondition(Map<String, Object> params);
    
}