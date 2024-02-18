package org.wzj.scheduler.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "监控详情")
public class MonitorDetail {

    @Schema(description = "数据库名称")
    private String databaseName;

    @Schema(description = "表名称")
    private String tableName;

    @Schema(description = "字段名称")
    private String fieldName;

    @Schema(description = "字段空值率")
    private Double fieldNullRate;


    @Override
    public String toString() {
        return "MonitorDetail{" +
                "databaseName='" + databaseName + '\'' +
                ", tableName='" + tableName + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", fieldNullRate=" + fieldNullRate +
                '}';
    }
}
