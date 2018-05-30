package com.corpdata.system.org.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.corpdata.system.org.entity.OrgDept;
import com.github.pagehelper.Page;

@Mapper
public interface OrgDeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrgDept record);

    OrgDept selectByPrimaryKey(String id);
    
    Page<OrgDept> selectAll();

    Page<OrgDept> selectAllByKeyword(@Param("keyword") String keyword);
    
    int updateByPrimaryKey(OrgDept record);
    
    List<Map<String,Object>> selectAllByCombox();
    
    /**
     * 按父分类查询最后一条子分类记录 编号（folderid）
     * @param parentFolderid
     * @return
     */
    String selectLastFolderidByParent(@Param("parentFolderid") String parentFolderid);
}