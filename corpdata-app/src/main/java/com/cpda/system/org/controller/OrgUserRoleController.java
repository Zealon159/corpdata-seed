package com.cpda.system.org.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.result.Result;
import com.cpda.system.org.dao.OrgRoleMapper;
import com.cpda.system.org.entity.OrgUserRole;
import com.cpda.system.org.service.OrgRoleService;
import com.cpda.system.org.service.OrgUserRoleService;
import com.cpda.system.org.service.OrgUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 用户角色关联控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/user-role")
public class OrgUserRoleController extends BaseController {

	@Autowired
	private OrgUserRoleService orgUserRoleService;

	@Autowired
	private OrgRoleMapper roleMapper;

	@Autowired
	private OrgUserService userService;

	@GetMapping("/seting/{userid}")
	public String seting(@PathVariable("userid") String userid, ModelMap map){
		map.put("userid",userid);
		map.put("roleList",roleMapper.selectByKeyword(null));
		return "/system/org/user/user_role_seting";
	}

	@PostMapping("/get-user-roles")
	@ResponseBody
	public Set<String> getUserRoles(String userid){
		return userService.getRolesByUser(userid);
	}

	@ResponseBody
	@RequestMapping("/add")
	public Result add(OrgUserRole record){
		return orgUserRoleService.save(record);
	}
	
	//保存用户角色
	@ResponseBody
	@RequestMapping("/save-user-roles")
	public Result saveUserRoles(String userId, String roles){
		return orgUserRoleService.createUserRoles(userId, roles);
	}

}
