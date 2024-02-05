package org.wzj.scheduler.service;

import org.wzj.scheduler.bean.SchedulerJobInfo;

import java.util.List;

public interface SchedulerService {
    List<SchedulerJobInfo> getAllJobs();
}
