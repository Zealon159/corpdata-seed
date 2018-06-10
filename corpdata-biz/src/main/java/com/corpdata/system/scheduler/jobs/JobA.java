package com.corpdata.system.scheduler.jobs;

import java.util.Date;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import com.corpdata.system.scheduler.BaseJob;
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  

public class JobA implements BaseJob {  

    private static Logger log = LoggerFactory.getLogger(JobA.class);  

    public void execute(JobExecutionContext context) throws JobExecutionException {  

    	log.info("JobAAAAA 执行时间: " + new Date());  

    }  
}
