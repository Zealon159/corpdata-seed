package com.corpdata.system.scheduler.job;

import java.util.Date;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;

import com.corpdata.system.scheduler.BaseJob;

import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;  

public class JobB implements BaseJob {  

    private static Logger _log = LoggerFactory.getLogger(JobB.class);  

    public JobB() {  

    }  

    public void execute(JobExecutionContext context)  
        throws JobExecutionException {  
        _log.error("JobB 执行时间: " + new Date());  

    }  
}
