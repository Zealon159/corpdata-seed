package com.cpda.system.org.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.org.entity.OrgUser;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface OrgUserMapper extends BaseMapper<OrgUser> {
	
    //OrgUser getUserInfoByUserid(@Param("userId") String userId);

    Page<OrgUser> selectAll(@Param("deptId") Long deptId);

    OrgUser selectByUserId(@Param("userId") String userId);

    List<Map<String,Object>> selectAllByCombox();

    List<OrgUser> selectByIds(String ids);

    //int selectCountByDept(@Param("deptId") String deptId);
    List<Map<String,Object>> getDoorsByUser(String staffId);

    @Select("SELECT id,user_name,Phone_number FROM org_user WHERE Phone_number =#{phone}")
    @ResultMap("BaseResultMap")
    List<OrgUser> selectByPhone(@Param("phone") String phone);
}