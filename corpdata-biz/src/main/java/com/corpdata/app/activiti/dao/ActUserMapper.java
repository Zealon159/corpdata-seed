package com.corpdata.app.activiti.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ActUserMapper {
	
	@Select("select * from act_id_user ")
	public List<Map<String,Object>> getAll();
	
	@Select(" select * from BPM_OrgUserList ")
	public List<Map<String,Object>> getBPMAll();
}
