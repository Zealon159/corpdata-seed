package com.corpdata.app.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.core.result.Result;
import com.corpdata.core.result.ResultGenerator;
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
        return ResultGenerator.genSuccessResult();
    }
	
	@ResponseBody
	@PostMapping("/update")
	public Result update(Test model) {
		testService.update(model);
        return ResultGenerator.genSuccessResult();
    }
	
	@ResponseBody
	@PostMapping("/listdata")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Test> list = testService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
	
	//delete
	@ResponseBody
	@PostMapping("/delete")
	public Result deleteByTotal(int total) {
        return testService.deleteByTotal(total);
    }
}
