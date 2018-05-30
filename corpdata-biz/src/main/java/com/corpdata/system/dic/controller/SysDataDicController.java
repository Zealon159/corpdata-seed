package com.corpdata.system.dic.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpdata.common.result.Result;
import com.corpdata.system.dic.entity.SysDataDic;
import com.corpdata.system.dic.service.SysDataDicService;
import com.corpdata.system.dic.service.SysDicTypeService;

@Controller
@RequestMapping("sys/sysDataDic")
public class SysDataDicController {
	
	@Autowired
	private SysDataDicService sysDataDicService;
	
	@Autowired
	private SysDicTypeService sysDicTypeService;
	
	@RequestMapping("/toadd")
	public String toadd(ModelMap map){
		return "system/dic/dic_add";
	}
	
	@RequestMapping("/tolist/{dictype}")
	public String tolist(ModelMap map,@PathVariable("dictype") String dictype){
		map.put("dictype", dictype);
		return "system/dic/dic_list";
	}
	
	@RequestMapping("/toindex")
	public String toindex(ModelMap map){
		map.put("pageJson", sysDicTypeService.getSysDicTypeNav());
		return "system/dic/dic_index";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Result add(SysDataDic record){
		return sysDataDicService.insert(record);
	}

	@RequestMapping("/toedit/{id}")
	public String toedit(ModelMap map,@PathVariable("id") String id){
		map.put("sysDataDic", sysDataDicService.selectByPrimaryKey(id));
		return "system/dic/dic_edit";
	}
	
	@ResponseBody
	@RequestMapping("/edit")
	public Result edit(SysDataDic record){
		return sysDataDicService.update(record);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public Result delete(String id,String dicType){
		return sysDataDicService.delete(id,dicType);
	}
	
	@ResponseBody
	@RequestMapping("/find/{dictype}/*")
	public String findByPage(HttpServletRequest request,@PathVariable("dictype") String dictype){
		int page = Integer.parseInt(request.getParameter("page"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String searchStr = request.getParameter("searchStr");
		return sysDataDicService.findByPage(page, limit, dictype, searchStr);
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
