package com.cpda.system.org.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.org.entity.OrgDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrgDeptMapper extends BaseMapper<OrgDept> {
	
    List<Map<String,Object>> selectAllByCombox();
    
    /**
     * 按父分类查询最后一条子分类记录 编号（folderid）
     * @param parentFolderid
     * @return
     */
    public String selectLastFolderidByParent(@Param("parentFolderid") String parentFolderid);
    
    public List<OrgDept> selectByParentId(@Param("parentId") String parentId);
    
    public String selectByFolderId(@Param("folderId") String folderId);
}