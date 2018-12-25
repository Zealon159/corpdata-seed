package com.cpda.system.org.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgUserRole;
import com.cpda.system.org.service.OrgUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
