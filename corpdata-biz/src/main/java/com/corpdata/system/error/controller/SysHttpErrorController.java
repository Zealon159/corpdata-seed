package com.corpdata.system.error.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.corpdata.system.error.entity.SysHttpError;
import com.corpdata.system.error.service.SysHttpErrorService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.system.log.WebLog;
import com.corpdata.core.base.BaseController;

/**
 * 系统HTTP请求异常
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-06-11 09:32:48
 */
 
@Controller
@RequestMapping("system/error")
public class SysHttpErrorController extends BaseController{

	@Autowired
	private SysHttpErrorService sysHttpErrorService;
	
	//新增页面
	@GetMapping("/add")
	public String add(){
		return "system/error/sysHttpError_add";
	}
	
	//保存数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/save")
	public Result save(SysHttpError record){
		return sysHttpErrorService.save(record);
	}
	
	//编辑页面
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id,ModelMap map){
		map.put("record", sysHttpErrorService.findById(id));
		return "system/error/sysHttpError_edit";
	}
	
	//更新数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/update")
	public Result update(SysHttpError record){
		return sysHttpErrorService.update(record);
	}
	
	//删除
	@WebLog()
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(String id){
		return sysHttpErrorService.deleteById(id);
	}
	
	//批量删除
	@WebLog()
	@ResponseBody
	@PostMapping("/deletes")
	public Result deletes(@RequestParam("ids[]") String[] ids){
		return sysHttpErrorService.deleteByIds(ids);
	}
	
	//详情页面
	@GetMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") String id){
		map.put("record", sysHttpErrorService.findById(id));
	    return "system/error/sysHttpError_details";
	}
	
	//列表页面
	@GetMapping("/list")
	public String list(){
	    return "system/error/sysHttpError_list";
	}
	
	//列表数据
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return sysHttpErrorService.findByPage(dgRequest);
	}
	
}
