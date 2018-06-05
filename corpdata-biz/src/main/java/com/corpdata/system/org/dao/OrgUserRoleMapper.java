package com.corpdata.system.org.dao;

import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgUserRole;
 
public interface OrgUserRoleMapper extends BaseMapper<OrgUserRole> {
	
    int deleteByUserid(String userid);

    int insertSelective(OrgUserRole record);

    OrgUserRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrgUserRole record);

}