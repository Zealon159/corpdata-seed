package com.corpdata.system.org.dao;

import com.corpdata.core.base.Mapper;
import com.corpdata.system.org.entity.OrgRole;
import com.github.pagehelper.Page;
import java.util.List;
import java.util.Map;


public interface OrgRoleMapper extends Mapper<OrgRole> {
	
    int deleteByPrimaryKey(String id);
    
    Page<OrgRole> selectAll(Map<String,Object> params);
    
    List<String> getRolesByUser(String userid);
    
    List<OrgRole> findOrgRoleList(Map<String, Object> params);
}