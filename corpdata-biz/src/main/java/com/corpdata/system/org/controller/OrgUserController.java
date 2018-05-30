package com.corpdata.system.org.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.system.org.entity.OrgUser;
import com.corpdata.system.org.service.impl.UserServiceImpl;
import com.corpdata.system.security.shiro.util.ShiroUserPwdUtil;
import com.corpdata.system.security.shiro.util.UserUtil;
import com.corpdata.common.result.Result;
import com.corpdata.common.utils.CorpdataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 系统用户控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("orguser")
public class OrgUserController {

	@Autowired
	private UserServiceImpl userService;
	
	@RequestMapping("/toadd")
	public String toadd(ModelMap map){
		map.put("id", CorpdataUtil.getUUID());
		return "system/org/user/user_add";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Result insert(OrgUser user,String orgDept){
		return userService.insert(user,orgDept);	
	}
	
	@RequestMapping("/toedit/{id}")
	public String toedit(@PathVariable String id,ModelMap m){
		OrgUser model = userService.selectByPrimaryKey(id);
		m.addAttribute("model", model);
		return "system/org/user/user_edit";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Result edit(OrgUser model,String orgDept){
		return userService.update(model,orgDept);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		return userService.delete(id);
	}

	@RequestMapping("/tolist")
	public String tolist(ModelMap map){
		return "system/org/user/user_list";
	}
	
	@ResponseBody
	@RequestMapping("/find/*")
	public String findByPage(HttpServletRequest request){
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String keyword = request.getParameter("keyword");
		String deptId = request.getParameter("deptId");
		String jsonData = userService.findByPage(page, limit, keyword, deptId);
		return jsonData;	
	}
	
	@RequestMapping("/tosel/{fdName}/{type}")
	public String tosel(ModelMap map, @PathVariable String fdName, @PathVariable String type){
		map.put("fdName", fdName);
		map.put("type", type);
		return "system/org/user/user_sel";
	}
	
	/**
	 * 跳转个人信息设置页面
	 * @return
	 */
	@RequestMapping("/toEditInformation")
	public String toEditInformation(ModelMap map){
		OrgUser user = userService.selectByPrimaryKey(UserUtil.getCurrentOrgUser().getId());
		map.put("user", user);
		return "system/org/user/user_edit_information";
	}
	
	/**
	 * 跳转密码修改页面
	 */
	@RequestMapping("/toModPassword")
	public String toModPassword(ModelMap map){
		OrgUser user = userService.selectByPrimaryKey(UserUtil.getCurrentOrgUser().getId());
		map.put("user", user);
		return "system/org/user/user_edit_password";
	}
	
	/**
	 * 校验密码输入是否正确
	 * @param oldPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPassword")
	public String checkPassword(String oldPassword){
		OrgUser user = userService.selectByPrimaryKey(UserUtil.getCurrentOrgUser().getId());
		boolean result = true;
		String password = ShiroUserPwdUtil.generateEncryptPwd(user.getUserid(), oldPassword);//密码加密
		//判断录入的密码是否跟数据库存储的一致
		if(!password.equals(user.getUserPwd())){
			result = false;
		}
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", result);
        ObjectMapper mapper = new ObjectMapper();
        String resultString = "";
        try {
            resultString = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultString;
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatePassword")
	public Result updatePassword(String id ,String newPassword){
		return userService.modPassword(id,newPassword);
	}
	
}
