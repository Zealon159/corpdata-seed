package com.corpdata.system.org.dao;

import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgRole;
import java.util.List;
import java.util.Map;

public interface OrgRoleMapper extends BaseMapper<OrgRole> {
	
    List<String> getRolesByUser(String userid);
    
    List<OrgRole> findOrgRoleList(Map<String, Object> params);
}