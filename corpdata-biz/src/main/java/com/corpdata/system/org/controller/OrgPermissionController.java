package com.corpdata.system.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpdata.core.base.BaseController;
import com.corpdata.system.org.service.impl.OrgPermissionServiceImpl;

/**
 * 系统权限控制器类
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/permission")
public class OrgPermissionController extends BaseController{
	
	@Autowired
	private OrgPermissionServiceImpl orgPermissionService;
	
	@ResponseBody
	@RequestMapping("/gettreejson")
	public String getTreeJson(){
		return orgPermissionService.selectAllTreeJsonData();
	}
	
	@ResponseBody
	@RequestMapping("/findRolePermmission")
	public String getPermissionByRole(String roleId){
		String permissionIds=orgPermissionService.selectPermissionIdByRoleId(roleId);
		return "{\"success\":\"true\",\"msg\":\""+permissionIds+"\"}";
	}

}
