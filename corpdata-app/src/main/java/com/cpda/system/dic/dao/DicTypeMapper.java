package com.cpda.system.dic.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.dic.entity.SysDicType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DicTypeMapper extends BaseMapper<SysDicType> {

    List<Map<String, Object>> selectAllByCombox();

    List<SysDicType> selectAllByCondition(Map<String, Object> params);

}