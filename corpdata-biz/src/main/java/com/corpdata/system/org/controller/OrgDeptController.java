package com.corpdata.system.org.controller;

import com.corpdata.system.org.service.OrgDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.system.log.WebLog;
import com.corpdata.system.org.entity.OrgDept;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.utils.CorpdataUtil;
import com.corpdata.core.base.BaseController;

/**
 * 组织控制器类
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/dept")
public class OrgDeptController extends BaseController{
	
	@Autowired
	private OrgDeptService orgDeptService;
	
	@GetMapping("/add")
	public String add(ModelMap map){
		map.put("id", CorpdataUtil.getUUID());
		return "system/org/dept/dept_add";
	}
	
	@WebLog()
	@ResponseBody
	@RequestMapping("/save")
	public Result save(OrgDept record){
		return orgDeptService.save(record);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id,ModelMap m){
		OrgDept model = orgDeptService.findById(id);
		m.addAttribute("model", model);
		return "system/org/dept/dept_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(OrgDept record,String oldParentFolderid){
		return orgDeptService.update(record,oldParentFolderid);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id){
		return orgDeptService.deleteById(id);
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map){
		return "system/org/dept/dept_list";
	}
	
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return orgDeptService.findByPage(dgRequest);
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
