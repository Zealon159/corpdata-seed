package com.cpda.system.menu.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.menu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *  持久层接口
 * @author Zealon
 * @date 2018-08-21 10:54:44
 * 
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    // 按返回类型查询
    public List<SysMenu> selectAll(@Param("parentId") Long parentId);
}
