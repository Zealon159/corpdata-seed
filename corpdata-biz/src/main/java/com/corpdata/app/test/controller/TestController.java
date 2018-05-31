package com.corpdata.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.common.result.util.ResultUtil;

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
    public String listData(DataGridRequestDTO dgRequest) {
		return testService.findByPage(dgRequest);
    }
	
	@ResponseBody
	@PostMapping("/delete")
	public Result deleteByTotal(String id) {
        return testService.deleteById(id);
    }
}
