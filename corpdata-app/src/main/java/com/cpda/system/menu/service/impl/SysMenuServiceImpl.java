package com.cpda.system.menu.service.impl;

import com.alibaba.fastjson.JSON;
import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.result.Result;
import com.cpda.common.result.util.ResultUtil;
import com.cpda.config.GlobalConstant;
import com.cpda.system.menu.dao.SysMenuMapper;
import com.cpda.system.menu.entity.SysMenu;
import com.cpda.system.menu.service.SysMenuService;
import com.cpda.system.org.dao.OrgRolePermissionMapper;
import com.cpda.system.security.shiro.util.UserUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * 
 * 菜单服务
 * @author Zealon
 * @date 2018-08-21 10:54:44
 */
@Service
@Transactional
public class SysMenuServiceImpl extends AbstractBaseService<SysMenu> implements SysMenuService {

    private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private SysMenuMapper menuMapper;

    @Autowired
    private OrgRolePermissionMapper rolePermissionMapper;

    /**
     * 新增菜单
     * @param record
     * @return
     */
    @Override
    public Result save(SysMenu record) {
        Result result = ResultUtil.success();
        try{
            // 保存菜单
            record.setState("open");
            super.save(record);

            // 更新父级状态
            updateParentState(0,record.getParentId(),0);

            logger.info("{} 新增菜单,Name:{}",UserUtil.getCurrentUserid(),record.getMenuName());

        }catch (Exception ex){
            result = ResultUtil.fail();
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 更新菜单
     * @param record
     * @return
     */
    @Override
    public Result update(SysMenu record, Long oldParentId) {
        Result result = ResultUtil.success();
        try{
            // 更新菜单
            super.update(record);

            // 更新父级状态
            updateParentState(1,record.getParentId(),oldParentId);

            logger.info("{} 修改菜单,Name:{}",UserUtil.getCurrentUserid(),record.getMenuName());
        }catch (Exception ex){
            result = ResultUtil.fail();
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @Override
    public Result deleteById(Long id, Long parentId) {
        Result result = ResultUtil.success();
        int subMenuCount = menuMapper.getSubMenuCountByParentId(id);
        if (subMenuCount>0){
            result = ResultUtil.fail("删除失败，请先移动或删除子菜单！");
        }else {
            try {
                // 删除
                super.deleteById(id);

                // 更新父级状态
                updateParentState(2,parentId,0);

                logger.info("{} 删除菜单,ID:{}",UserUtil.getCurrentUserid(),id);
            } catch (Exception ex) {
                result = ResultUtil.fail();
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param returnMode
     * @return
     */
    @Override
    //@Cacheable(value="menu")
    public String findByPage(int page,int rows,String returnMode,Long parentId) {

        //增加返回模式，如果是数组，则直接返回List的Json字符串数组
        PageHelper.startPage(page, rows);
        Page<SysMenu> list = (Page<SysMenu>) menuMapper.selectByParentId(parentId);
        return JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd");
    }

    @Override
    public String getMenuJson(int mode) {
        return "{"+getMenusByParentId(0,mode)+"}";
    }

    /**
     * 递归查询所有菜单
     * @param pid:上级id
     * @param mode:是否递归查询全部子数据（ mode=0 时查询全部）
     * @return
     */
    public String getMenusByParentId(long pid,int mode){
        StringBuffer sb = new StringBuffer();
        sb.append("\"menus\":[");


        List<SysMenu> list = menuMapper.selectByParentId(pid);
        for(int i=0;i<list.size();i++){
            if(i>0){
                sb.append(",");
            }
            SysMenu menu = list.get(i);
            sb.append("{\"menuid\":\"").append(menu.getId()).append("\",");
            sb.append("\"icon\":\"").append(menu.getIcon()).append("\",");
            sb.append("\"menuname\":\"").append(menu.getMenuName()).append("\",");
            sb.append("\"menuType\":\"").append(getMenuTypeCN(menu.getMenuType())).append("\",");
            sb.append("\"parentId\":\"").append(menu.getParentId()).append("\",");
            sb.append("\"url\":\"").append(menu.getMenuUrl()).append("\"");

            //mode为0时代表同步获取全部数据，继续获取子分类
            if(mode==0) {
                sb.append(",").append(getMenusByParentId(menu.getId(), mode));
            }
            sb.append("}");
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public SysMenu findById(Long id) {
        return super.findById(id);
    }

    /**
     * 获取combotree 数据源
     * @param mode 0：同步 、 1：异步
     * @param roleId 角色id（非0，则根据角色追加选中属性）
     * @return
     */
    @Override
    public String getTreeJson(int mode,long roleId) {
        StringBuffer root = new StringBuffer();
        Set<Long> rolePermissions = null;
        if(roleId != 0){
            rolePermissions = rolePermissionMapper.getOrgRolePermissionSet(roleId);
        }
        root.append("[{\"id\":0");
        root.append(",\"text\":\"root\"");
        root.append(",\"children\":").append(getMenuListByPid(menuMapper.selectByParentId(0L),rolePermissions));
        root.append("}]");
        return root.toString();
    }

    /**
     * 递归查询全部菜单
     * @param list
     * @param rolePermissions : 角色所有权限（不为空增加选中状态）
     * @return
     */
    public String getMenuListByPid(List<SysMenu> list, Set<Long> rolePermissions){
        StringBuffer json = new StringBuffer();
        json.append("[");
        if (list!=null){
            for (int i = 0; i < list.size(); i++) {
                // 第二行头部需追加逗号
                if (i>0) json.append(",");

                SysMenu menu = list.get(i);
                json.append("{");
                json.append("\"id\":").append(menu.getId());
                json.append(",\"text\":\"").append(menu.getMenuName()).append("\"");

                // 角色所有权限（不为空增加选中状态）
                if(rolePermissions!=null && rolePermissions.contains(menu.getId())){
                    json.append(",\"checked\":true");
                }

                // 当前状态为closed，需要继续查询子菜单 children
                if (menu.getState().equals("closed")) {
                    json.append(",\"children\":");
                    json.append(getMenuListByPid(menuMapper.selectByParentId(menu.getId()),rolePermissions));
                }

                json.append("}");
            }
        }
        json.append("]");
        return json.toString();
    }

    /**
     * 获取菜单类型中文名
     * @param menuType
     * @return
     */
    private String getMenuTypeCN(String menuType){
        String cn = "--";
        if (menuType.equals(GlobalConstant.MENU_DIR)){
            cn = "目录";
        }else if (menuType.equals(GlobalConstant.MENU_BUTTON)){
            cn = "按钮";
        }else if (menuType.equals(GlobalConstant.MENU_MENU)){
            cn = "菜单";
        }
        return cn;
    }

    /**
     * 维护父级菜单的 state 状态（close/open）
     * @param mode 操作模式 0:新增, 1:编辑 , 2:删除
     * @param parentId
     * @param oldParentId
     */
    private void updateParentState(int mode,long parentId,long oldParentId){
        if (mode==0){
            // 直接更新父级菜单state状态为 closed
            if (parentId!=0) {
                SysMenu menu = new SysMenu(parentId,"closed");
                menuMapper.updateState(menu);
                logger.debug("{} 更新父级菜单,ID:{}，state状态为 closed",UserUtil.getCurrentUserid(),parentId);
            }

        }else if (mode==1){
            // 1.更新父级 为 closed
            if (parentId!=0) {
                SysMenu menu = new SysMenu(parentId, "closed");
                menuMapper.updateState(menu);
                logger.debug("{} 更新父级菜单,ID:{}，state状态为 closed",UserUtil.getCurrentUserid(),parentId);
            }

            // 2.更新原父级状态（需检查是否有子菜单）
            checkAndUpdateParentState(oldParentId);
        }else {
            // 更新父级状态
            checkAndUpdateParentState(parentId);
        }
    }

    /**
     * 检查并更新父级 state 状态
     * @param parentId
     */
    private void checkAndUpdateParentState(long parentId){
        if (parentId!=0) {
            int subMenuCount = menuMapper.getSubMenuCountByParentId(parentId);
            String state = "closed";
            if (subMenuCount == 0){
                state = "open";
            }
            SysMenu parentMenu = new SysMenu(parentId,state);
            menuMapper.updateState(parentMenu);

            logger.debug("{} 更新父级菜单,ID:{}，state状态为 {}",UserUtil.getCurrentUserid(),parentId,state);
        }
    }

}
