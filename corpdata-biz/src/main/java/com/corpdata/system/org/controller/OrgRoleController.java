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
import com.corpdata.common.result.Result;
import com.corpdata.common.utils.CorpdataUtil;

/**
 * 系统角色控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("orgrole")
public class OrgRoleController {
	
	@Autowired
	private OrgRoleService orgRoleService;
	
	@RequestMapping("/toadd")
	public String toadd(ModelMap map){
		map.put("id", CorpdataUtil.getUUID());
		return "system/org/role/role_add";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Result add(OrgRole record){
		return orgRoleService.insert(record);
	}
	
	@RequestMapping("/tolist")
	public String tolist(ModelMap map){
		return "system/org/role/role_list";
	}
	
	@RequestMapping("/toedit/{id}")
	public String toedit(ModelMap map,@PathVariable("id") String id){
		map.put("orgrole", orgRoleService.selectByPrimaryKey(id));
		return "system/org/role/role_edit";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Result edit(OrgRole record){
		return orgRoleService.update(record);
	}
	
	@ResponseBody
	@RequestMapping("/find/*")
	public String findByPage(HttpServletRequest request){
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String searchStr = request.getParameter("searchStr");
		return orgRoleService.findByPage(page, limit, searchStr);
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
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		return orgRoleService.delete(id);
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
