package com.cpda.system.org.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.system.org.entity.OrgDept;
import com.cpda.system.org.service.OrgDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 组织控制器类
 * @author zealon
 * @date 2018年3月1日
 */
@Controller
@RequestMapping("system/org/dept")
public class OrgDeptController extends BaseController {
	
	@Autowired
	private OrgDeptService orgDeptService;
	
	@GetMapping("/add")
	public String add(){
		return "system/org/dept/dept_add";
	}

	@ResponseBody
	@RequestMapping("/save")
	public Result save(OrgDept record){
		return orgDeptService.save(record);
	}
	
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable Long id,ModelMap m){
		OrgDept model = orgDeptService.findById(id);
		String parentModelId=orgDeptService.findByFolderid(model.getParentfolderid());
		m.addAttribute("record", model);
		m.addAttribute("parentId", parentModelId);
		return "system/org/dept/dept_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(OrgDept record, String oldParentFolderid){
		return orgDeptService.update(record,oldParentFolderid);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(Long id){
		return orgDeptService.deleteById(id);
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map){
		return "system/org/dept/dept_list";
	}
	
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return orgDeptService.findByPage(dgRequest.getPage(),dgRequest.getRows());
	}
	
	/**
	 * 获取组织下拉数据源
	 * @param val 0:无根目录，1:有根目录
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectjson/{val}")
	public String getSelectJsonData(@PathVariable String val){
		boolean hashRoot = val.equals("0") ? false : true;
		return orgDeptService.findByCombox(hashRoot);
	}
	/**
	 * 获取树数据表格
	 * @param val
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectformjsontree")
	public String getSelectFormTreeJsonData(String id){
		if(id==null){
			id="0";				
		}
		return orgDeptService.findByFromJsonTree(id);
	}
}
