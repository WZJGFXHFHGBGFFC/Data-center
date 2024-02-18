package org.wzj.scheduler.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import org.wzj.scheduler.bean.SchedulerJobInfo;
import org.wzj.scheduler.service.impl.SchedulerServiceImpl;
import java.util.List;

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
    public Result<String> pauseJob(@RequestBody SchedulerJobInfo jobInfo) throws SchedulerException {
        //暂停作业
        schedulerService.pauseJob(jobInfo);

        return Result.build(ResultCodeEnum.SUCCESS, "任务暂停");
    }

    /**
     * 重启作业
     */
    @PostMapping("resumeJob")
    public Result<String> resumeJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        schedulerService.resumeJob(jobInfo);
        return Result.build(ResultCodeEnum.SUCCESS, "任务重启");
    }

    /**
     * 删除作业
     */
    @PostMapping("deleteJob")
    public Result<String> deleteJob(@RequestBody SchedulerJobInfo jobInfo) throws Exception {
        // 删除作业
        schedulerService.deleteJob(jobInfo);

        return Result.build(ResultCodeEnum.SUCCESS, "任务删除");
    }

    /**
     * 创建一个新作业
     */
    @PostMapping("createJob/{databaseName}/{tableName}/{fieldName}")
    public Result<String> createJob(@RequestBody SchedulerJobInfo jobInfo, @PathVariable String databaseName, @PathVariable String tableName, @PathVariable String fieldName) throws Exception {

        // 构建并存储新建任务
        schedulerService.addJob(jobInfo, databaseName, tableName, fieldName);

        return Result.build(ResultCodeEnum.SUCCESS, "创建任务成功");
    }
}
