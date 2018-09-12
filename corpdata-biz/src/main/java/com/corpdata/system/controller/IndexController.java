package com.corpdata.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("system")
public class IndexController {
	
	@RequestMapping("")
	public String index(){
		return "system/sys_index";
	}

	@RequestMapping("/index")
	public String index1(){
		return "system/index1";
	}
	
	@RequestMapping("post-tool")
	public String postTool(){
		return "system/post_tool";
	}
}
