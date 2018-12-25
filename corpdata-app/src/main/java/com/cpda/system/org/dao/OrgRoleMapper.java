package com.cpda.system.org.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.org.entity.OrgRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface OrgRoleMapper extends BaseMapper<OrgRole> {
	
    List<String> getRolesByUser(String userid);
    
    List<OrgRole> selectByKeyword(@Param("keyword") String keyword);

    @Select("select id,role_name text from org_role")
	List<Map<String, String>> selectData();
}