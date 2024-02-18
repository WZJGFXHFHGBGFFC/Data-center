package org.wzj.scheduler.utils;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.wzj.scheduler.bean.SchedulerJobInfo;
import org.wzj.scheduler.job.MySQLMonitorJob;
import org.wzj.scheduler.job.SimpleJob;

import java.util.AbstractMap;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 构建新任务工具类
 */
public class BuildJob {
    public static AbstractMap.SimpleEntry<JobDetail, Trigger> buildJob(SchedulerJobInfo jobInfo, String databaseName, String tableName, String fieldName, Long triggerCount){
        // 构建JobDataMap以便在需要时添加数据
        var jobData = new JobDataMap();
        if (!jobInfo.getJobType().equals("简单任务(测试)")) {
            jobData.put("databaseName", databaseName);
            jobData.put("tableName", tableName);
            jobData.put("fieldName", fieldName);
        }

        // 根据任务类型创建对应的JobDetail(三元操作符)
        JobDetail jobDetail = JobBuilder.newJob(jobInfo.getJobType().equals("简单任务(测试)") ? SimpleJob.class : MySQLMonitorJob.class)
                .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                .usingJobData(jobData) // 如果是简单任务，这里的jobData实际上是空的，不会影响任务执行
                .build();

        // 创建触发器，根据是否是CronJob选择不同的配置(三元操作符)
        Trigger trigger = jobInfo.getCronJob() ?
                newTrigger()
                        .withIdentity("trigger" + triggerCount++, jobInfo.getJobGroup())
                        .withSchedule(cronSchedule(jobInfo.getCronExpression()))
                        .build() :
                newTrigger()
                        .withIdentity("trigger" + triggerCount++, jobInfo.getJobGroup())
                        .startNow()
                        .withSchedule(simpleSchedule()
                                .withIntervalInSeconds(jobInfo.getRepeatTime())
                                .repeatForever())
                        .build();
        return new AbstractMap.SimpleEntry<>(jobDetail, trigger);
    }
}
