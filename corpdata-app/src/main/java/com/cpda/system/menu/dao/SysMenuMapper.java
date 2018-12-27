package com.cpda.system.menu.dao;

import com.cpda.common.base.BaseMapper;
import com.cpda.system.menu.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *  持久层接口
 * @author Zealon
 * @date 2018-08-21 10:54:44
 * 
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据父id查询菜单列表
     * @param parentId 父id
     * @return
     */
    public List<SysMenu> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据父id查询子菜单数量
     * @param parentId
     * @return
     */
    @Select("select count(*) from sys_menu where parent_id=#{parentId}")
    public int getSubMenuCountByParentId(@Param("parentId") Long parentId);

    /**
     * 更新状态
     * @param record
     * @return
     */
    public int updateState(SysMenu record);

    /**
     * 根据用户获取相应权限集合
     * @param userId
     * @return
     */
    public List<String> getPermissionsByUser(String userId);
}
