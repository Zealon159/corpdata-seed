package com.cpda.system.menu.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.result.Result;
import com.cpda.system.menu.entity.SysMenu;
import com.cpda.system.menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public Result save(@Valid SysMenu record){
        return menuService.save(record);
    }

    //编辑页面
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap map){
        map.put("record", menuService.findById(id));
        return "system/menu/menu_edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping("/update")
    public Result update(@Valid SysMenu record, long oldParentId){
        return menuService.update(record,oldParentId);
    }

    //删除
    @ResponseBody
    @PostMapping("/delete")
    public Result delete(Long id, Long parentId){
        return menuService.deleteById(id,parentId);
    }

    //批量删除
    @ResponseBody
    @PostMapping("/deletes")
    public Result deletes(@RequestParam("ids[]") Long[] ids){
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
    public String findByPage(Long id){
        String returnMode = "";
        if(id==null){
            id = 0L;
        }
        if(id!=-1){
            returnMode = "array";
        }
        return menuService.findByPage(1,1000,returnMode,id);
    }

    //下拉列表数据源
    @ResponseBody
    @RequestMapping("/comboxjson")
    public String getComboxJsondata(){
        String returnMode = "array";
        long parentId = 0;
        return menuService.findByPage(1,1000,returnMode,parentId);
    }

    //菜单组件数据源
    @ResponseBody
    @RequestMapping("/jsondata")
    public String getJsonData(){
        return menuService.getMenuJson(0);
    }

    //下拉树数据源
    @ResponseBody
    @RequestMapping("/comtree-json/{roleId}")
    public String getTreeJson(@PathVariable("roleId") long roleId){
        return menuService.getTreeJson(0,roleId);
    }

}
