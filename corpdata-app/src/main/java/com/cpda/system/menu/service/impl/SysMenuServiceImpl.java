package com.cpda.system.menu.service.impl;

import com.alibaba.fastjson.JSON;
import com.cpda.common.base.AbstractBaseService;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.system.menu.dao.SysMenuMapper;
import com.cpda.system.menu.entity.SysMenu;
import com.cpda.system.menu.service.SysMenuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 菜单服务
 * @author Zealon
 * @date 2018-08-21 10:54:44
 */
@Service
@Transactional
public class SysMenuServiceImpl extends AbstractBaseService<SysMenu> implements SysMenuService {

    @Autowired
    private SysMenuMapper menuMapper;

    @Override
    public String findByPage(DataGridRequestDTO dgRequest) {

        Map<String,Object> params = dgRequest.getParams();

        //增加返回模式，如果是数组，则直接返回List的Json字符串数组
        if(params!=null && params.get("returnMode")!=null && params.get("returnMode").equals("array")){
            PageHelper.startPage(dgRequest.getPage(), dgRequest.getRows());
            Page<SysMenu> list = (Page<SysMenu>) mapper.selectAll(dgRequest.getParams());
            return JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd");
        }else {
            return super.findByPage(dgRequest);
        }
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

        Map<String, Object> params = new HashMap<>();
        params.put("parentId",pid);
        List<SysMenu> list = menuMapper.selectAll(params);
        for(int i=0;i<list.size();i++){
            if(i>0){
                sb.append(",");
            }
            SysMenu menu = list.get(i);
            sb.append("{\"menuid\":\""+menu.getId()+"\",");
            sb.append("\"icon\":\""+menu.getIcon()+"\",");
            sb.append("\"menuname\":\""+menu.getMenuName()+"\",");
            sb.append("\"parentId\":\""+menu.getParentId()+"\",");
            sb.append("\"url\":\""+menu.getMenuUrl()+"\"");

            //mode为0时代表同步获取全部数据，继续获取子分类
            if(mode==0) {
                sb.append("," + getMenusByParentId(menu.getId(), mode));
            }
            sb.append("}");
        }

        sb.append("]");
        return sb.toString();
    }
}
