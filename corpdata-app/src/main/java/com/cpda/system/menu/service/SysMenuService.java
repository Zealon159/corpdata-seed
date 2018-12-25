package com.cpda.system.menu.service;

import com.cpda.common.base.BaseService;
import com.cpda.common.result.Result;
import com.cpda.system.menu.entity.SysMenu;

/**
 * 系统菜单服务
 */
public interface SysMenuService extends BaseService<SysMenu> {
    /**
     * 获取菜单数据源
     * @param mode 模式，0同步、1异步
     * @return
     */
	String getMenuJson(int mode);


    /**
     * 更新
     * @param record
     * @param oldParentId 原父id
     * @return
     */
    Result update(SysMenu record, Long oldParentId);

    /**
     * 删除
     * @param id
     * @param parentId 父id
     * @return
     */
    Result deleteById(Long id, Long parentId);

                         /**
     * 查询分页
     * @param page
     * @param rows
     * @param returnMode 返回模式
     * @param parentId 父id
     * @return
     */
    String findByPage(int page, int rows, String returnMode, Long parentId);

    /**
     * 获取combotree 数据源
     * @param mode 0：同步 、 1：异步 << !! 暂未实现 >>
     * @param roleId 角色id（非0，则根据角色追加选中属性）
     * @return
     */
    String getTreeJson(int mode, long roleId);

}
