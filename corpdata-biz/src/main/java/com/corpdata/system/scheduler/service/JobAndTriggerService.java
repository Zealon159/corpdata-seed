package com.corpdata.system.scheduler.service;

import com.corpdata.system.scheduler.entity.JobAndTrigger;
import com.github.pagehelper.PageInfo;

public interface JobAndTriggerService {
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
