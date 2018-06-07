package com.corpdata.system.scheduler.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.corpdata.system.scheduler.entity.JobAndTrigger;

@Mapper
public interface JobAndTriggerMapper {
	
	public List<JobAndTrigger> getJobAndTriggerDetails();
	
}
