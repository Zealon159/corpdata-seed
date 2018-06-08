package com.corpdata.system.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corpdata.system.scheduler.service.JobAndTriggerService;

/**
 * 参考：
 * https://github.com/tjfy1992/SpringBootQuartz/tree/master/demo/src/main/java/com/example/demo
 * https://blog.csdn.net/u012907049/article/details/73801122
 */
@Controller
@RequestMapping(value="system/scheduler")
public class JobController { 
	
	@Autowired
	private JobAndTriggerService jobAndTriggerService;
	
	@GetMapping(value="/add")
	public String add() {			
		return "system/scheduler/job_add";
	}
	
	//新增任务
	@ResponseBody
	@PostMapping(value="/save")
	public void save(@RequestParam(value="jobClassName")String jobClassName, 
			@RequestParam(value="jobGroupName")String jobGroupName, 
			@RequestParam(value="cronExpression")String cronExpression) throws Exception {
		
		jobAndTriggerService.addJob(jobClassName, jobGroupName, cronExpression);
	}
	
	//暂停
	@ResponseBody
	@PostMapping(value="/pause")
	public void pause(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception{			
		jobAndTriggerService.jobPause(jobClassName, jobGroupName);
	}

	//继续任务
	@ResponseBody
	@PostMapping(value="/resume")
	public void resume(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception
	{			
		jobAndTriggerService.jobresume(jobClassName, jobGroupName);
	}
	
	//重新部署任务
	@ResponseBody
	@PostMapping(value="/reschedule")
	public void reschedule(@RequestParam(value="jobClassName")String jobClassName, 
			@RequestParam(value="jobGroupName")String jobGroupName,
			@RequestParam(value="cronExpression")String cronExpression) throws Exception{			
		jobAndTriggerService.jobreschedule(jobClassName, jobGroupName, cronExpression);
	}
	
	//删除任务
	@ResponseBody
	@PostMapping(value="/delete")
	public void delete(@RequestParam(value="jobClassName")String jobClassName, @RequestParam(value="jobGroupName")String jobGroupName) throws Exception {			
		jobAndTriggerService.jobdelete(jobClassName, jobGroupName);
	}
	
	//查询任务
	@GetMapping(value="/list")
	public String list() {			
		return "system/scheduler/job_list";
	}
	
	//查询任务
	@ResponseBody
	@PostMapping(value="/listdata")
	public String queryjob(@RequestParam(value="page")Integer page, @RequestParam(value="limit")Integer limit) {			
		return jobAndTriggerService.getJobAndTriggerDetails(page, limit);
	}
	 
}
