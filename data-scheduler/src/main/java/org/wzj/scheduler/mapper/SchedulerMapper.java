package org.wzj.scheduler.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.scheduler.bean.SchedulerJobInfo;

import java.util.List;

@Mapper
public interface SchedulerMapper {

    //     * 获取所有作业
    List<SchedulerJobInfo> getAllJobs();
}
