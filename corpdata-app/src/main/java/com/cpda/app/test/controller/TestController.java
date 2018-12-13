package com.cpda.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.cpda.app.test.entity.Test;
import com.cpda.app.test.service.TestService;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.common.base.BaseController;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-12-13 15:39:31
 */
 
@Controller
@RequestMapping("app/test")
public class TestController extends BaseController{

	@Autowired
	private TestService testService;
	
	//新增页面
	@GetMapping("/add")
	public String add(){
		return "app/test/test_add";
	}
	
	//保存数据
	@ResponseBody
	@RequestMapping("/save")
	public Result save(Test record){
		return testService.save(record);
	}
	
	//编辑页面
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id,ModelMap map){
		map.put("record", testService.findById(id));
		return "app/test/test_edit";
	}
	
	//更新数据
	@ResponseBody
	@RequestMapping("/update")
	public Result update(Test record){
		return testService.update(record);
	}
	
	//删除
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(Long id){
		return testService.deleteById(id);
	}
	
	//批量删除
	@ResponseBody
	@PostMapping("/deletes")
	public Result deletes(@RequestParam("ids[]") Long[] ids){
		return testService.deleteByIds(ids);
	}
	
	//详情页面
	@GetMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") Long id){
		map.put("record", testService.findById(id));
	    return "app/test/test_details";
	}
	
	//列表页面
	@GetMapping("/list")
	public String list(){
	    return "app/test/test_list";
	}
	
	//列表数据
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		System.out.println(dgRequest.getRows());
		return testService.findByPage(dgRequest.getPage(),dgRequest.getRows());
	}
	
}
