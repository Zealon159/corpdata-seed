package com.cpda.system.org.controller;


import com.cpda.common.base.BaseController;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgUser;
import com.cpda.system.org.service.OrgUserService;
import com.cpda.system.security.shiro.util.ShiroUserPwdUtil;
import com.cpda.system.security.shiro.util.UserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户控制器
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/user")
public class OrgUserController extends BaseController {

	@Autowired
	private OrgUserService userService;
	
	/*@Autowired
	private ProjectTeamService projectTeamService;
	
	@Autowired
	private UserDeptService userDeptService;
	
	@Autowired
	private OrgUserRoleService orgUserRoleService;*/

	
	@RequestMapping("/add")
	public String add(ModelMap map){
		return "system/org/user/user_add";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Result save(OrgUser record, Long orgDept, String deptids, String roleProject){
		return userService.insert(record,orgDept,deptids,roleProject);	
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id,ModelMap m){
		OrgUser model = userService.findById(id);
		m.addAttribute("model", model);
		//m.addAttribute("userRole",orgUserRoleService.findByUserId(model.getUserid()));
		return "system/org/user/user_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(OrgUser record,Long orgDept,String deptids,String sysAttachmentPortraitId,String roleProject){
		return userService.update(record,orgDept,deptids,sysAttachmentPortraitId,roleProject);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(Long id){

		return userService.deleteById(id);
	}

	@RequestMapping("/list")
	public String list(ModelMap map){
		return "system/org/user/user_list";
	}
	
	@RequestMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") Long id){
		OrgUser ou=userService.findById(id);
		map.put("user", ou);
		return "system/org/user/user_details";
	}
	
	@RequestMapping("/index")
	@RequiresPermissions("user:view")
	public String index(ModelMap map){
		return "system/org/user/user_index";
	}
	
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		Long deptId = null;
		if (dgRequest.getParams()!=null && dgRequest.getParams().get("deptId")!=null){
			deptId = Long.parseLong(dgRequest.getParams().get("deptId").toString());
		}
		return userService.findByPage(dgRequest.getPage(),dgRequest.getRows(),deptId);
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
	@RequestMapping("/toSetIndex")
	public String toEditInformation(ModelMap map){
		OrgUser user = userService.findById(UserUtil.getCurrentOrgUser().getId());
		map.put("user", user);
		return "system/org/user/user_set_index";
	}
	
	@RequestMapping("/toSetXinxi")
	public String toSetXinxi(ModelMap map){
		OrgUser user = userService.findById(UserUtil.getCurrentOrgUser().getId());
		map.put("user", user);
		return "system/org/user/user_set_information";
	}
	
	/**
	 * 跳转密码修改页面
	 */
	@RequestMapping("/toModPassword")
	public String toModPassword(ModelMap map){
		OrgUser user = userService.findById(UserUtil.getCurrentOrgUser().getId());
		map.put("user", user);
		return "system/org/user/user_set_password";
	}
	
	/**
	 * 校验密码输入是否正确
	 * @param oldPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkPassword")
	public String checkPassword(String oldPassword){
		OrgUser user = userService.findById(UserUtil.getCurrentOrgUser().getId());
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
	public Result updatePassword(Long id ,String newPassword){
		return userService.modPassword(id,newPassword);
	}
	/**
	 * 获取用户下来数据源
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectjson")
	public String getSelectJsonData(){
		return userService.findByCombox();
	}
	
}
