package com.corpdata.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.corpdata.app.test.entity.Test;
import com.corpdata.app.test.service.TestService;
import com.corpdata.common.domain.DataGridRequestDTO;
import com.corpdata.common.result.Result;
import com.corpdata.system.log.WebLog;

@Controller
@RequestMapping("/app/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping("/add")
    public String add() {
        return "/app/test/add";
    }
	
	@WebLog(name="")
	@ResponseBody
	@PostMapping("/save")
	public Result save(Test model) {
		return testService.save(model);
    }
	
	@GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id) {
        return "/app/test/edit";
    }
	
	@ResponseBody
	@PostMapping("/update")
	public Result update(Test model) {
		return testService.update(model);
    }
	
	@ResponseBody
	@PostMapping("/delete")
	public Result delete(String id) {
        return testService.deleteById(id);
    }

    @PostMapping("/detail/{id}")
    public String detail(ModelMap map,@PathVariable("id") String id) {
        Test model = testService.findById(id);
        map.put("model", model);
        return "";
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

}
