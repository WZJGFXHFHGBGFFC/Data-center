package org.wzj.scheduler.mapper.monitJobMapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.scheduler.bean.MonitorDetail;

@Mapper
public interface MySQLMonitorJobMapper {
    MonitorDetail MySQLMonitorSelect(MonitorDetail monitorDetail);

    void MySQLMonitorResult(MonitorDetail monitorDetail);
}
