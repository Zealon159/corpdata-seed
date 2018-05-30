package com.corpdata.system.org.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.system.org.entity.OrgDept;
import com.corpdata.system.org.service.OrgDeptService;
import com.corpdata.common.result.Result;
import com.corpdata.common.utils.CorpdataUtil;

/**
 * 组织控制器类
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("orgdept")
public class OrgDeptController {
	
	@Autowired
	private OrgDeptService orgDeptService;
	
	@RequestMapping("/toadd")
	public String toadd(ModelMap map){
		map.put("id", CorpdataUtil.getUUID());
		return "system/org/dept/dept_add";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Result add(OrgDept record){
		return orgDeptService.insert(record);
	}
	
	@RequestMapping("/toedit/{id}")
	public String toedit(@PathVariable String id,ModelMap m){
		OrgDept model = orgDeptService.selectByPrimaryKey(id);
		m.addAttribute("model", model);
		return "system/org/dept/dept_edit";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Result edit(OrgDept record,String oldParentFolderid){
		return orgDeptService.update(record,oldParentFolderid);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		return orgDeptService.delete(id);
	}
	
	@RequestMapping("/tolist")
	public String tolist(ModelMap map){
		return "system/org/dept/dept_list";
	}
	
	@ResponseBody
	@RequestMapping("/find/*")
	public String findByPage(HttpServletRequest request){
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String keyword = request.getParameter("keyword");
		return orgDeptService.findByPage(page, limit ,keyword);
	}
	
	/**
	 * 获取组织下来数据源
	 * @param val 0:无根目录，1:有根目录
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectjson/{val}")
	public String getSelectJsonData(@PathVariable String val){
		boolean hashRoot = val.equals("0") ? false : true;
		return orgDeptService.findByCombox(hashRoot);
	}
}
