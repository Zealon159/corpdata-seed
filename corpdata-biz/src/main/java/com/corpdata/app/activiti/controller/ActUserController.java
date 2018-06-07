package com.corpdata.app.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corpdata.app.activiti.service.ActUserService;

@RestController
@RequestMapping("app/actuser")
public class ActUserController {
	
	@Autowired
	private ActUserService userService;
	
	@RequestMapping("/all")
	public String getAll(){
		return userService.getAll();
	}
	
	@RequestMapping("/bpmall")
	public String getBPMAll(){
		return userService.getBPMAll();
	}
}
