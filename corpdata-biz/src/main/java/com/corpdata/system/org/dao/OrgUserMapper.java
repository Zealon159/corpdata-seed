package com.corpdata.system.org.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.corpdata.system.org.entity.OrgUser;
import com.github.pagehelper.Page;

@Mapper
public interface OrgUserMapper {
	
    int deleteByPrimaryKey(String id);

    int insert(OrgUser record);

    OrgUser selectByPrimaryKey(String id);
    //,@Param("pwd") String pwd
    OrgUser getUserInfoByUserid(@Param("userId") String userId);

    Page<OrgUser> selectAll();
    
    Page<OrgUser> selectAllByKeyword(@Param("keyword") String keyword,@Param("deptId") String deptId);

    int updateByPrimaryKey(OrgUser record);
    
    OrgUser selectByUserId(String userId);
    
    //int selectCountByDept(@Param("deptId") String deptId);
}