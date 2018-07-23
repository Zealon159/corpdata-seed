package com.corpdata.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.corpdata.app.test.entity.TbCar;
import com.corpdata.app.test.service.TbCarService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.system.log.WebLog;
import com.corpdata.core.base.BaseController;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-07-23 11:28:57
 */
 
@Controller
@RequestMapping("app/car")
public class TbCarController extends BaseController{

	@Autowired
	private TbCarService tbCarService;
	
	//新增页面
	@GetMapping("/add")
	public String add(){
		return "app/test/tbCar_add";
	}
	
	//保存数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/save")
	public Result save(TbCar record){
		return tbCarService.save(record);
	}
	
	//编辑页面
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("carName") String carName,ModelMap map){
		map.put("record", tbCarService.findById(carName));
		return "app/test/tbCar_edit";
	}
	
	//更新数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/update")
	public Result update(TbCar record){
		return tbCarService.update(record);
	}
	
	//删除
	@WebLog()
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(String carName){
		return tbCarService.deleteById(carName);
	}
	
	//批量删除
	@WebLog()
	@ResponseBody
	@PostMapping("/deletes")
	public Result deletes(@RequestParam("ids[]") String[] carNames){
		return tbCarService.deleteByIds(carNames);
	}
	
	//详情页面
	@GetMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") String id){
		map.put("record", tbCarService.findById(id));
	    return "app/test/tbCar_details";
	}
	
	//列表页面
	@GetMapping("/list")
	public String list(){
	    return "app/test/tbCar_list";
	}
	
	//列表数据
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return tbCarService.findByPage(dgRequest);
	}
	
}
