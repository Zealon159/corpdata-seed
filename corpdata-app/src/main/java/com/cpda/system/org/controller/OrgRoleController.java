package com.cpda.system.org.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgRole;
import com.cpda.system.org.service.OrgRolePermissionService;
import com.cpda.system.org.service.OrgRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 系统角色控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/role")
public class OrgRoleController extends BaseController {
	
	@Autowired
	private OrgRoleService orgRoleService;

	@Autowired
	private OrgRolePermissionService orgRolePermissionService;
	
	@RequestMapping("/add")
	public String add(ModelMap map){
		return "system/org/role/role_add";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Result save(OrgRole record){
		return orgRoleService.save(record);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(ModelMap map, @PathVariable("id") long id){
		map.put("record", orgRoleService.findById(id));
		return "system/org/role/role_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(OrgRole record){
		return orgRoleService.update(record);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(long id){
		return orgRoleService.deleteById(id);
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map){
		return "system/org/role/role_list";
	}
	
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest, String keyword){
		return orgRoleService.findByKeyword(dgRequest.getPage(),dgRequest.getRows(),keyword);
	}
	
	@ResponseBody
	@RequestMapping("/select-data")
	public List<Map<String, String>> findByPage(){
		return orgRoleService.selectData();
	}
	
	//角色权限分配页面
	@RequestMapping("/permission-seting/{roleId}")
	public String toPermissionManage(ModelMap map, @PathVariable("roleId") long roleId){
		map.addAttribute("roleId", roleId);
		return "system/org/role/role_permission_seting";
	}
	
	//保存角色权限
	@ResponseBody
	@RequestMapping("/save-role-permission")
	public Result savePermission(long roleId, String permissionIds){
		return orgRolePermissionService.saveRolePermission(roleId,permissionIds);
	}

	@ResponseBody
	@RequestMapping("/get-role-permission")
	public Set<Object> getRolePermission(){
		Set<Object> set = new HashSet<>();
		set.add(1);
		set.add(3);
		set.add(7);
		return set;
	}
	
	@RequestMapping("/assign/{userId}")
	public String toassign(ModelMap map, @PathVariable("userId") String userId){

		//获取所有角色
		//List<OrgRole> list = orgRoleService.findOrgRoleList();
		List<String> roleslist=orgRoleService.getRolesByUser(userId);
		String userRoles = "";
		int i = 0;

		for (String roles:roleslist) {
			if(i>0){userRoles += ",";}
			userRoles += roles;
			i++;
		}

		map.put("userRoles", userRoles);
		map.put("userId", userId);
		//map.put("list", list);
		
		return "system/org/user/assign-user-roles";
	}
	
}
