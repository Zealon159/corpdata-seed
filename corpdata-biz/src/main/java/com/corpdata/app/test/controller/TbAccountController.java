package com.corpdata.app.test.controller;

import com.corpdata.app.test.exception.NotSufficientFundsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.corpdata.app.test.entity.TbAccount;
import com.corpdata.app.test.service.TbAccountService;
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
@RequestMapping("app/account")
public class TbAccountController extends BaseController{

	@Autowired
	private TbAccountService tbAccountService;
	
	//新增页面
	@GetMapping("/add")
	public String add(){
		return "app/test/tbAccount_add";
	}
	
	//保存数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/save")
	public Result save(TbAccount record){
		return tbAccountService.save(record);
	}
	
	//编辑页面
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("uid") String uid,ModelMap map){
		map.put("record", tbAccountService.findById(uid));
		return "app/test/tbAccount_edit";
	}
	
	//更新数据
	@WebLog()
	@ResponseBody
	@RequestMapping("/update")
	public Result update(TbAccount record){
		return tbAccountService.update(record);
	}
	
	//删除
	@WebLog()
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(String uid){
		return tbAccountService.deleteById(uid);
	}
	
	//批量删除
	@WebLog()
	@ResponseBody
	@PostMapping("/deletes")
	public Result deletes(@RequestParam("ids[]") String[] uids){
		return tbAccountService.deleteByIds(uids);
	}
	
	//详情页面
	@GetMapping("/details/{id}")
	public String details(ModelMap map,@PathVariable("id") String id){
		map.put("record", tbAccountService.findById(id));
	    return "app/test/tbAccount_details";
	}
	
	//列表页面
	@GetMapping("/list")
	public String list(){
	    return "app/test/tbAccount_list";
	}
	
	//列表数据
	@ResponseBody
	@RequestMapping("/listdata")
	public String findByPage(DataGridRequestDTO dgRequest){
		return tbAccountService.findByPage(dgRequest);
	}

	@ResponseBody
	@RequestMapping("/buy-car")
	public Object buyCar(String uid,String carName,int number){
		tbAccountService.buyCar(uid,carName,number);
		return "ok";
	}
}
