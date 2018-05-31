package com.corpdata.app.test.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.common.base.DataGridRequestEntity;
import com.corpdata.common.result.DataGrid;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/app/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@ResponseBody
	@PostMapping("/add")
	public Result add(Test model) {
		testService.save(model);
        return ResultUtil.success();
    }
	
	@ResponseBody
	@PostMapping("/update")
	public Result update(Test model) {
		testService.update(model);
        return ResultUtil.success();
    }
	
	@GetMapping("/list")
    public String list() {
        return "/app/test/list";
    }
	
	@ResponseBody
	@PostMapping("/listdata")
    public DataGrid list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        PageHelper.startPage(page, limit);
        List<Test> list = testService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return null;
    }
	
	
	@ResponseBody
	@PostMapping("/listdata2")
    public String list2(@RequestBody DataGridRequestEntity dgRequest) {//@RequestBody 
        System.out.println("v:"+dgRequest.getParams());
        System.out.println("page:"+dgRequest.getPage());
        System.out.println("limit:"+dgRequest.getLimit());
        List<Test> list = testService.findBy(dgRequest.getParams());
        return JSON.toJSONString(list);
    }
	
	//delete
	@ResponseBody
	@PostMapping("/delete")
	public Result deleteByTotal(int total) {
        return testService.deleteByTotal(total);
    }
}
