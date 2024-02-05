package org.wzj.scheduler.service.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wzj.scheduler.bean.SchedulerJobInfo;
import org.wzj.scheduler.mapper.SchedulerMapper;
import org.wzj.scheduler.service.SchedulerService;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    @Autowired
    private SchedulerMapper schedulerMapper;

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
    public static void addJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "123456"
            );
            var insertStatement = connection.prepareStatement(
                    "INSERT INTO scheduler_job_info (jobName, jobGroup, jobStatus, repeatTime, jobClass, cronJob, cronExpression, jobType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.setString(3, jobInfo.getJobStatus());
            insertStatement.setInt(4, jobInfo.getRepeatTime());
            insertStatement.setString(5, jobInfo.getJobClass());
            insertStatement.setBoolean(6, jobInfo.getCronJob());
            insertStatement.setString(7, jobInfo.getCronExpression());
            insertStatement.setString(8, jobInfo.getJobType());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "123456"
            );
            var insertStatement = connection.prepareStatement(
                    "UPDATE scheduler_job_info SET jobStatus = '已暂停' WHERE jobName = ? AND jobGroup = ?"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重启任务
     */
    public static void resumeJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/yuntai_schedule?useSSL=false&characterEncoding=utf8",
                    "root",
                    "123456"
            );
            var insertStatement = connection.prepareStatement(
                    "UPDATE scheduler_job_info SET jobStatus = '运行中' WHERE jobName = ? AND jobGroup = ?"
            );
            insertStatement.setString(1, jobInfo.getJobName());
            insertStatement.setString(2, jobInfo.getJobGroup());
            insertStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除某个任务
     */
    public static void deleteJob(SchedulerJobInfo jobInfo) {
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.10.140:3306/yuntai_schedule?useSSL=false",
                    "root",
                    "123456"
            );
            var deleteStatement = connection.prepareStatement(
                    "DELETE FROM scheduler_job_info WHERE id = ?"
            );
            deleteStatement.setLong(1, jobInfo.getId());
            deleteStatement.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
