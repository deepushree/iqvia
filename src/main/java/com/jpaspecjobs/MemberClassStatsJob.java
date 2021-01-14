package com.jpaspecjobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpaspecconfig.QuartzConfig;
import com.jpaspecservice.MemberClassService;

@Slf4j
@Component
@DisallowConcurrentExecution
public class MemberClassStatsJob implements Job {
    @Autowired
    MemberClassService memberClassService;

    private static Logger log = LoggerFactory.getLogger(MemberClassStatsJob.class);
    
    @Override
    public void execute(JobExecutionContext context) {
    	
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        memberClassService.classStats();
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
