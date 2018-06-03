package com.corpdata.system.org.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.system.org.entity.OrgRole;
import com.corpdata.system.org.service.OrgRoleService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.utils.CorpdataUtil;

/**
 * 系统角色控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/role")
public class OrgRoleController {
	
	@Autowired
	private OrgRoleService orgRoleService;
	
	@RequestMapping("/add")
	public String add(ModelMap map){
		map.put("id", CorpdataUtil.getUUID());
		return "system/org/role/role_add";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Result save(OrgRole record){
		return orgRoleService.insert(record);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(ModelMap map,@PathVariable("id") String id){
		map.put("orgrole", orgRoleService.selectByPrimaryKey(id));
		return "system/org/role/role_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(OrgRole record){
		return orgRoleService.update(record);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		return orgRoleService.delete(id);
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map){
		return "system/org/role/role_list";
	}
	
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return orgRoleService.findByPage(dgRequest);
	}
	
	//角色权限分配页面
	@RequestMapping("/topermissionmanage/{roleId}")
	public String toPermissionManage(ModelMap map,@PathVariable("roleId") String roleId){
		map.addAttribute("roleId", roleId);
		return "system/org/role/role_permission_manage";
	}
	
	//保存角色权限
	@ResponseBody
	@RequestMapping("/savepermission")
	public Result savePermission(String roleId,String permissionIds){
		return orgRoleService.saveRolePermission(roleId, permissionIds);
	}
	
	@RequestMapping("/assign/{userId}")
	public String toassign(ModelMap map, @PathVariable("userId") String userId){
		//获取所有角色
		List<OrgRole> list = orgRoleService.findOrgRoleList();
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
		map.put("list", list);
		
		return "system/org/user/assign-user-roles";
	}
	
}
