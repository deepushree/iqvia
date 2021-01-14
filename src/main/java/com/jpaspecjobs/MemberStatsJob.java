package com.jpaspecjobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpaspecservice.MemberService;

@Slf4j
@Component
@DisallowConcurrentExecution
public class MemberStatsJob implements Job {
    @Autowired
    private MemberService memberService;

    private static Logger log = LoggerFactory.getLogger(MemberStatsJob.class);
    
    @Override
    public void execute(JobExecutionContext context) {
        log.info("Job ** {} ** starting @ {}", context.getJobDetail().getKey().getName(), context.getFireTime());
        memberService.memberStats();
        log.info("Job ** {} ** completed.  Next job scheduled @ {}", context.getJobDetail().getKey().getName(), context.getNextFireTime());
    }
}
