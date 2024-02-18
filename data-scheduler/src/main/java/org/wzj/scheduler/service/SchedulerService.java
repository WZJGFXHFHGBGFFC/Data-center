package org.wzj.scheduler.service;

import org.quartz.SchedulerException;
import org.wzj.scheduler.bean.SchedulerJobInfo;

import java.util.List;

public interface SchedulerService {

    /**
     * 获取所有作业
     */
    List<SchedulerJobInfo> getAllJobs();

    /**
     * 存储新建任务
     */
    void addJob(SchedulerJobInfo jobInfo, String databaseName, String tableName, String fieldName) throws SchedulerException;

    /**
     * 暂停任务
     */
    void pauseJob(SchedulerJobInfo jobInfo) throws SchedulerException;

    /**
     * 重启任务
     */
    void resumeJob(SchedulerJobInfo jobInfo) throws SchedulerException;

    /**
     * 删除作业
     */
    void deleteJob(SchedulerJobInfo jobInfo) throws SchedulerException;
}
