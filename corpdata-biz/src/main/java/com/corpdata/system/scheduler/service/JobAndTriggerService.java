package com.corpdata.system.scheduler.service;


public interface JobAndTriggerService {
	/**
	 * 查询任务
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getJobAndTriggerDetails(int page, int limit);
	
	/**
	 * 创建调度任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @param cronExpression
	 */
	void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception;
	
	/**
	 * 暂停任务
	 * @param jobClassName
	 * @param jobGroupName
	 */
	void jobPause(String jobClassName, String jobGroupName) throws Exception;
	
	/**
	 * 继续执行任务
	 * @param jobClassName
	 * @param jobGroupName
	 */
	void jobresume(String jobClassName, String jobGroupName) throws Exception;
	
	/**
	 * 重新部署任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @param cronExpression
	 */
	void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception;
	
	/**
	 * 删除任务
	 * @param jobClassName
	 * @param jobGroupName
	 * @throws Exception
	 */
	void jobdelete(String jobClassName, String jobGroupName) throws Exception;
}
