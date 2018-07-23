package com.corpdata.system.dic.controller;

import java.util.HashMap;
import java.util.Map;
import com.corpdata.system.dic.service.SysDataDicService;
import com.corpdata.system.dic.service.SysDicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.core.base.BaseController;
import com.corpdata.system.dic.entity.SysDataDic;

@Controller
@RequestMapping("system/data-dic")
public class SysDataDicController extends BaseController{
	
	@Autowired
	private SysDataDicService sysDataDicService;
	
	@Autowired
	private SysDicTypeService sysDicTypeService;
	
	@RequestMapping("/add")
	public String add(ModelMap map){
		return "system/dic/dic_add";
	}
	
	@RequestMapping("/list/{dictype}")
	public String list(ModelMap map,@PathVariable("dictype") String dictype){
		map.put("dictype", dictype);
		return "system/dic/dic_list";
	}
	
	@RequestMapping("/index")
	public String toindex(ModelMap map){
		map.put("pageJson", sysDicTypeService.getSysDicTypeNav());
		return "system/dic/dic_index";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Result save(SysDataDic record){
		return sysDataDicService.save(record);
	}

	@RequestMapping("/edit/{id}")
	public String edit(ModelMap map,@PathVariable("id") String id){
		map.put("sysDataDic", sysDataDicService.findById(id));
		return "system/dic/dic_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(SysDataDic record){
		return sysDataDicService.update(record);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id,String dicType){
		return sysDataDicService.delete(id,dicType);
	}
	
	@ResponseBody
	@RequestMapping("/listdata/{dictype}")
	public String findByPage(DataGridRequestDTO dgRequest,@PathVariable("dictype") String dictype){
		Map<String,Object> params = dgRequest.getParams();
		if(params==null){
			params = new HashMap<String,Object>();
		}
		if(!dictype.equals("0")){  //0代表查询全部
			params.put("dictype", dictype);
			dgRequest.setParams(params);
		}
		return sysDataDicService.findByPage(dgRequest);
	}
	
	@ResponseBody
	@RequestMapping("/selectjson/{code}")
	public String getSelectJsonData(@PathVariable("code") String code){
		return sysDataDicService.findByComboxByCode(code);
	}
	
	@ResponseBody
	@RequestMapping("/getDataDicText/{id}")
	public String getDataDicText(@PathVariable("id") String id){
		return sysDataDicService.getDataDicText(id);
	}
}
