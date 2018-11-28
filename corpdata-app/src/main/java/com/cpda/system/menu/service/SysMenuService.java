package com.cpda.system.menu.service;

import com.cpda.common.base.BaseService;
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
}
