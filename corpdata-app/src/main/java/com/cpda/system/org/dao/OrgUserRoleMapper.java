package com.cpda.system.org.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.org.entity.OrgUserRole;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrgUserRoleMapper extends BaseMapper<OrgUserRole> {
	
    int deleteByUserid(String userid);

    int insertSelective(OrgUserRole record);

    int updateByPrimaryKeySelective(OrgUserRole record);

    @Select("select * from org_user_role where userid=#{userid}")
    @ResultMap("BaseResultMap")
	List<OrgUserRole> selectByUserId(String userid);
}