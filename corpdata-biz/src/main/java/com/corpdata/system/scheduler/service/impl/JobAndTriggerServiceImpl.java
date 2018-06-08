package com.corpdata.system.scheduler.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.corpdata.common.api.pagehelper.PageConvertUtil;
import com.corpdata.core.datasource.DataSourceEnum;
import com.corpdata.core.datasource.aop.DynamicSwitchDataSource;
import com.corpdata.system.scheduler.BaseJob;
import com.corpdata.system.scheduler.dao.JobAndTriggerMapper;
import com.corpdata.system.scheduler.entity.JobAndTrigger;
import com.corpdata.system.scheduler.service.JobAndTriggerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class JobAndTriggerServiceImpl implements JobAndTriggerService {
	
	private static Logger log = LoggerFactory.getLogger(JobAndTriggerServiceImpl.class); 
	
	@Autowired
	private JobAndTriggerMapper jobAndTriggerMapper;
	
	@Autowired 
	@Qualifier("Scheduler")  //加入Qulifier注解，通过名称注入bean
	private Scheduler scheduler;
	
	@DynamicSwitchDataSource(dataSource = DataSourceEnum.QUARTZ)
	public String getJobAndTriggerDetails(int page, int limit) {
		PageHelper.startPage(page, limit);
		Page<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
		return PageConvertUtil.getGridJson(list);
	}
	
	public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception{
        // 启动调度器  
		scheduler.start(); 
		
		//构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();
		
		//表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName).withSchedule(scheduleBuilder).build();
        
        try {
        	scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
        	log.error("创建定时任务失败"+e);
            throw new Exception("创建定时任务失败");
        }
	}
	
	public void jobPause(String jobClassName, String jobGroupName) throws Exception{	
		scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
	}
	
	public void jobresume(String jobClassName, String jobGroupName) throws Exception {
		scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
	}
	

	public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {				
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			System.out.println("更新定时任务失败"+e);
			throw new Exception("更新定时任务失败");
		}
	}
	
	/**
	 * 删除任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @throws Exception
	 */
	public void jobdelete(String jobClassName, String jobGroupName) throws Exception{		
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));				
	}
	
	public static BaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (BaseJob)class1.newInstance();
	}
}
