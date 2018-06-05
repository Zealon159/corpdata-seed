package com.corpdata.system.org.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgUser;
import com.github.pagehelper.Page;

public interface OrgUserMapper extends BaseMapper<OrgUser>{
	
    OrgUser getUserInfoByUserid(@Param("userId") String userId);

    Page<OrgUser> selectAll(Map<String,Object> params);
    
    OrgUser selectByUserId(String userId);
    
    //int selectCountByDept(@Param("deptId") String deptId);
}