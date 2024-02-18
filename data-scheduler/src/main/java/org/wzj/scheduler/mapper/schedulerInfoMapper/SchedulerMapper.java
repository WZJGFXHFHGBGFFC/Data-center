package org.wzj.scheduler.mapper.schedulerInfoMapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.scheduler.bean.SchedulerJobInfo;

import java.util.List;

@Mapper
public interface SchedulerMapper {

    // 获取所有作业
    List<SchedulerJobInfo> getAllJobs();

    //暂停任务
    void pauseJob(SchedulerJobInfo jobInfo);

    //重启任务
    void resumeJob(SchedulerJobInfo jobInfo);

    // 删除某个任务
    void deleteJob(SchedulerJobInfo jobInfo);

    // 添加新的需要调度的任务
    void addJob(SchedulerJobInfo jobInfo);
}
