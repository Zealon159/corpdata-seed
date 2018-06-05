package com.corpdata.system.org.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.corpdata.core.base.BaseMapper;
import com.corpdata.system.org.entity.OrgDept;

public interface OrgDeptMapper extends BaseMapper<OrgDept> {
	
    List<Map<String,Object>> selectAllByCombox();
    
    /**
     * 按父分类查询最后一条子分类记录 编号（folderid）
     * @param parentFolderid
     * @return
     */
    String selectLastFolderidByParent(@Param("parentFolderid") String parentFolderid);
}