package com.corpdata.app.fruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.corpdata.app.fruit.entity.Fruit;
import com.corpdata.app.fruit.service.FruitService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.system.log.WebLog;

/**
 * 
 * 
 * @author zealon
 * @email zealon@126.com
 * @date 2018-06-05 22:20:22
 */
 
@Controller
@RequestMapping("app/fruit")
public class FruitController{

	@Autowired
	private FruitService fruitService;
	
	//新增页面
	@GetMapping("/add")
	public String add(){
		return "app/fruit/fruit_add";
	}
	
	//保存数据
	@ResponseBody
	@RequestMapping("/save")
	public Result save(Fruit record){
		return fruitService.save(record);
	}
	
	//编辑页面
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") String id,ModelMap map){
		map.put("record", fruitService.findById(id));
		return "app/fruit/fruit_edit";
	}
	
	//更新数据
	@ResponseBody
	@RequestMapping("/update")
	public Result update(Fruit record){
		return fruitService.update(record);
	}
	
	//删除
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(String id){
		return fruitService.deleteById(id);
	}
	
	//批量删除
	@ResponseBody
	@PostMapping("/deletes")
	public Result deletes(@RequestParam("ids[]") String[] ids){
		return fruitService.deleteByIds(ids);
	}
	
	//详情页面
	@GetMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") String id){
		map.put("record", fruitService.findById(id));
	    return "app/fruit/fruit_details";
	}
	
	//列表页面
	@GetMapping("/list")
	public String list(){
	    return "app/fruit/fruit_list";
	}
	
	//列表数据
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return fruitService.findByPage(dgRequest);
	}
	
}
