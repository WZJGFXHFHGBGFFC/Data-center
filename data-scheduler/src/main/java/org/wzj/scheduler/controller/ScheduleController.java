package org.wzj.scheduler.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import org.wzj.scheduler.bean.QuartzScheduler;
import org.wzj.scheduler.bean.SchedulerJobInfo;
import org.wzj.scheduler.job.MySQLMonitorJob;
import org.wzj.scheduler.job.SimpleJob;
import org.wzj.scheduler.service.impl.SchedulerServiceImpl;
import java.util.List;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Tag(name = "调度详情")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private SchedulerServiceImpl schedulerService;

    /**
     * 获取所有作业
     */
    @Operation(summary = "获取所有作业")
    @GetMapping("getAllJobs")
    public Result<List<SchedulerJobInfo>> getAllJobs() {
        return Result.build(ResultCodeEnum.SUCCESS, schedulerService.getAllJobs());
    }

    /**
     * 暂停作业
     */
    @PostMapping("pauseJob")
    public Result<String> pauseJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        jobInfo.setJobStatus("已暂停");
        SchedulerServiceImpl.pauseJob(jobInfo);
        return Result.build(ResultCodeEnum.SUCCESS, "任务暂停");
    }

    /**
     * 重启作业
     */
    @PostMapping("resumeJob")
    public Result<String> resumeJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        jobInfo.setJobStatus("运行中");
        SchedulerServiceImpl.resumeJob(jobInfo);
        return Result.build(ResultCodeEnum.SUCCESS, "任务重启");
    }

    /**
     * 删除作业
     */
    @PostMapping("deleteJob")
    public Result<String> deleteJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        var scheduler = QuartzScheduler.getInstance();
        scheduler.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
        SchedulerServiceImpl.deleteJob(jobInfo);
        return Result.build(ResultCodeEnum.SUCCESS, "任务删除");
    }

    /**
     * 创建一个新作业
     */
    private static int triggerCount = 0;

    @PostMapping("createJob/{databaseName}/{tableName}/{fieldName}")
    public Result<String> createJob(@RequestBody SchedulerJobInfo jobInfo, @PathVariable String databaseName, @PathVariable String tableName, @PathVariable String fieldName) throws Exception {
        var scheduler = QuartzScheduler.getInstance();

        // 判断是什么任务，然后新建对应的任务
        JobDetail jobDetail;
        if (jobInfo.getJobType().equals("简单任务(测试)")) {
            jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup()).build();
        } else {
            var jobData = new JobDataMap();
            jobData.put("databaseName", databaseName);
            jobData.put("tableName", tableName);
            jobData.put("fieldName", fieldName);
            jobDetail = JobBuilder
                    .newJob(MySQLMonitorJob.class)
                    .withIdentity(jobInfo.getJobName(), jobInfo.getJobGroup())
                    .setJobData(jobData)
                    .build();
        }

        // 新建触发器
        Trigger trigger;
        if (jobInfo.getCronJob()) {
            trigger = newTrigger()
                    .withIdentity("trigger" + triggerCount, jobInfo.getJobGroup())
                    .withSchedule(cronSchedule(jobInfo.getCronExpression()))
                    .build();
        } else {
            trigger = newTrigger()
                    .withIdentity("trigger" + triggerCount, jobInfo.getJobGroup())
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(jobInfo.getRepeatTime())
                            .repeatForever())
                    .build();
        }

        triggerCount++;

        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);

        // 将任务存入MySQL
        jobInfo.setJobStatus("运行中");
        SchedulerServiceImpl.addJob(jobInfo);

        return Result.build(ResultCodeEnum.SUCCESS, "创建任务成功");
    }
}
