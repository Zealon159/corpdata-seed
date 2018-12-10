package com.cpda.system.dic.controller;

import com.cpda.common.base.BaseController;
import com.cpda.common.domain.DataGridRequestDTO;
import com.cpda.common.result.Result;
import com.cpda.system.dic.entity.SysDataDic;
import com.cpda.system.dic.service.DataDicService;
import com.cpda.system.dic.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("system/data-dic")
public class DataDicController extends BaseController {

	@Autowired
	private DataDicService dataDicService;

	@Autowired
	private DicTypeService dicTypeService;
	
	@RequestMapping("/add")
	public String add(ModelMap map){
		return "system/dic/dic_add";
	}
	
	@RequestMapping("/list/{dictype}")
	public String list(ModelMap map, @PathVariable("dictype") String dictype){
		map.put("dictype", dictype);
		return "system/dic/dic_list";
	}
	
	@RequestMapping("/index")
	public String toindex(ModelMap map){
		map.put("pageJson", dicTypeService.getSysDicTypeNav());
		return "system/dic/dic_index";
	}
	
	@ResponseBody
	@RequestMapping("/save")
	public Result save(SysDataDic record){
		return dataDicService.save(record);
	}

	@RequestMapping("/edit/{id}")
	public String edit(ModelMap map, @PathVariable("id") Long id){
		map.put("sysDataDic", dataDicService.findById(id));
		return "system/dic/dic_edit";
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Result update(SysDataDic record){
		return dataDicService.update(record);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(Long id,String dicType){
		return dataDicService.delete(id,dicType);
	}
	
	@ResponseBody
	@RequestMapping("/listdata/{dictype}")
	public String findByPage(DataGridRequestDTO dgRequest, @PathVariable("dictype") String dictype){
		Map<String,Object> params = dgRequest.getParams();
		if(params==null){
			params = new HashMap<String,Object>();
		}
		if(!dictype.equals("0")){  //0代表查询全部
			params.put("dictype", dictype);
			dgRequest.setParams(params);
		}
		return dataDicService.findByPage(dgRequest.getPage(),dgRequest.getRows());
	}
	
	@ResponseBody
	@RequestMapping("/selectjson/{code}")
	public String getSelectJsonData(@PathVariable("code") String code){
		return dataDicService.findByComboxByCode(code);
	}
	
	@ResponseBody
	@RequestMapping("/getDataDicText/{id}")
	public String getDataDicText(@PathVariable("id") Long id){
		return dataDicService.getDataDicText(id);
	}
}
