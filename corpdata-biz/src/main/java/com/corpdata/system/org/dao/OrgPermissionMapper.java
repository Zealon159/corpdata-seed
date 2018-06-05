package com.corpdata.system.org.dao;

import java.util.List;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgPermission;

public interface OrgPermissionMapper extends BaseMapper<OrgPermission>{
	
    List<OrgPermission> selectAll();

    List<String> getPermissionsByUser(String userid);
    
}