package org.wzj.scheduler.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "调度任务信息")
public class SchedulerJobInfo {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "任务类型")
    private String jobType;

    @Schema(description = "任务名称")
    private String jobName;

    @Schema(description = "任务组")
    private String jobGroup;

    @Schema(description = "任务状态")
    private String jobStatus;

    @Schema(description = "任务类名")
    private String jobClass;

    @Schema(description = "Cron表达式")
    private String cronExpression;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "接口名称")
    private String interfaceName;

    @Schema(description = "重复次数")
    private Integer repeatTime;

    @Schema(description = "Cron任务")
    private Boolean cronJob;

    // Getters and Setters
}
