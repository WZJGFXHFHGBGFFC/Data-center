package org.wzj.scheduler.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.transaction.annotation.Transactional;
import org.wzj.scheduler.bean.MonitorDetail;
import org.wzj.scheduler.config.ApplicationContextProvider;
import org.wzj.scheduler.mapper.monitJobMapper.MySQLMonitorJobMapper;


public class MySQLMonitorJob implements Job{

    @Transactional
    @Override
    public void execute(JobExecutionContext jobExecutionContext)  throws JobExecutionException {
        // 获取任务参数
        var dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        var databaseName = dataMap.getString("databaseName");
        var tableName = dataMap.getString("tableName");
        var fieldName = dataMap.getString("fieldName");

        // 创建MonitorDetail对象
        MonitorDetail monitorDetail = new MonitorDetail(databaseName, tableName, fieldName, null);

        // 获取MySQLMonitorJobMapper的bean
        MySQLMonitorJobMapper mySQLMonitorJobMapper = ApplicationContextProvider.getApplicationContext().getBean(MySQLMonitorJobMapper.class);

        // 调用MySQLMonitorSelect方法获取监控结果
        MonitorDetail sqlMonitorJob = mySQLMonitorJobMapper.MySQLMonitorSelect(monitorDetail);
        monitorDetail.setFieldNullRate(sqlMonitorJob.getFieldNullRate());

        // 将监控结果写入数据库
        mySQLMonitorJobMapper.MySQLMonitorResult(monitorDetail);
    }
}
