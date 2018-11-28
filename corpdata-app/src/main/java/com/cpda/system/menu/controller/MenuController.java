package com.cpda.system.menu.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.common.utils.CommonUtil;
import com.cpda.system.menu.entity.SysMenu;
import com.cpda.system.menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther: Zealon
 * @Date: 2018-08-20 13:59
 */
@Controller
@RequestMapping("system/menu")
public class MenuController extends BaseController {

    @Autowired
    private SysMenuService menuService;

    //新增页面
    @GetMapping("/add")
    public String add(){
        return "system/menu/menu_add";
    }

    //保存数据
    @ResponseBody
    @RequestMapping("/save")
    public Result save(SysMenu record){
        return menuService.save(record);
    }

    //编辑页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, ModelMap map){
        map.put("record", menuService.findById(id));
        return "system/menu/menu_edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping("/update")
    public Result update(SysMenu record){
        return menuService.update(record);
    }

    //删除
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(String id){
        return menuService.deleteById(id);
    }

    //批量删除
    @ResponseBody
    @PostMapping("/deletes")
    public Result deletes(@RequestParam("ids[]") String[] ids){
        return menuService.deleteByIds(ids);
    }

    //列表页面
    @GetMapping("/list")
    public String list(){
        return "system/menu/menu_list";
    }

    //列表数据
    @ResponseBody
    @RequestMapping("/listdata")
    public String findByPage(String id){
        DataGridRequestDTO dgRequest = new DataGridRequestDTO();
        Map<String,Object> params = new HashMap<String,Object>();
        if(CommonUtil.isBlank(id)){
            id = "root";
        }
        if(!id.equals("root")){
            params.put("returnMode","array");
        }
        params.put("parentId",id);
        dgRequest.setParams(params);
        return menuService.findByPage(dgRequest);
    }

    //下拉列表数据源
    @ResponseBody
    @RequestMapping("/comboxjson")
    public String getComboxJsondata(){
        DataGridRequestDTO dgRequest = new DataGridRequestDTO();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("returnMode","array");
        params.put("parentId","root");
        dgRequest.setParams(params);
        return menuService.findByPage(dgRequest);
    }

    //菜单组件数据源
    @ResponseBody
    @RequestMapping("/jsondata")
    public String getJsonData(){
        return menuService.getMenuJson(0);
    }
}
