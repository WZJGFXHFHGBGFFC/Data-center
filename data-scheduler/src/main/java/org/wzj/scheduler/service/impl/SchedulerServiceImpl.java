package org.wzj.scheduler.service.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wzj.scheduler.bean.QuartzScheduler;
import org.wzj.scheduler.bean.SchedulerJobInfo;
import org.wzj.scheduler.mapper.schedulerInfoMapper.SchedulerMapper;
import org.wzj.scheduler.service.SchedulerService;
import org.wzj.scheduler.utils.BuildJob;

import java.util.AbstractMap;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerMapper schedulerMapper;

    // 从QuartzScheduler类获取Quartz调度器的单例实例
    Scheduler scheduler = QuartzScheduler.getScheduler();


    // 任务总执行次数
    private static Long triggerCount = 0L;

    /**
     * 获取所有调度的任务
     */
    @Override
    public  List<SchedulerJobInfo> getAllJobs() {
        return schedulerMapper.getAllJobs();
    }

    /**
     * 添加新的需要调度的任务
     */
    @Override
    public void addJob(SchedulerJobInfo jobInfo, String databaseName, String tableName, String fieldName) throws SchedulerException {

        // 通过构建新任务工具类获取新任务信息
        AbstractMap.SimpleEntry<JobDetail, Trigger> jobDetailTriggerSimpleEntry = BuildJob.buildJob(jobInfo, databaseName, tableName, fieldName, triggerCount);

        // 调度任务
        scheduler.scheduleJob(jobDetailTriggerSimpleEntry.getKey(), jobDetailTriggerSimpleEntry.getValue());
        scheduler.start();

        // 添加新的需要调度的任务
        jobInfo.setJobStatus("运行中");
        schedulerMapper.addJob(jobInfo);
    }

    /**
     * 暂停任务
     */
    @Override
    public  void pauseJob(SchedulerJobInfo jobInfo) throws SchedulerException {

        // 调用scheduler的pauseJob方法，传入一个新建的JobKey对象（由jobInfo提供的任务名称和任务组构成）来唯一标识并暂停对应的任务。
        scheduler.pauseJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));

        // 调用schedulerMapper的pauseJob方法，更新数据库，标记该任务为暂停状态。
        jobInfo.setJobStatus("已暂停");
        schedulerMapper.pauseJob(jobInfo);
    }

    /**
     * 重启任务
     */
    @Override
    public void resumeJob(SchedulerJobInfo jobInfo) throws SchedulerException {

        // 调用scheduler的pauseJob方法，传入一个新建的JobKey对象（由jobInfo提供的任务名称和任务组构成）来唯一标识并暂停对应的任务。
        scheduler.resumeJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));

        // 重启任务
        jobInfo.setJobStatus("运行中");
        schedulerMapper.resumeJob(jobInfo);
    }

    /**
     * 删除某个任务
     */
    @Override
    public void deleteJob(SchedulerJobInfo jobInfo) throws SchedulerException {
        // 调用scheduler的pauseJob方法，传入一个新建的JobKey对象（由jobInfo提供的任务名称和任务组构成）来唯一标识并暂停对应的任务。
        scheduler.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));

        // 删除某个任务
        schedulerMapper.deleteJob(jobInfo);
    }
}
