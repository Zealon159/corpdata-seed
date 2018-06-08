package com.corpdata.system.scheduler.dao;

import org.apache.ibatis.annotations.Mapper;
import com.corpdata.system.scheduler.entity.JobAndTrigger;
import com.github.pagehelper.Page;

@Mapper
public interface JobAndTriggerMapper {
	
	public Page<JobAndTrigger> getJobAndTriggerDetails();
	
}
