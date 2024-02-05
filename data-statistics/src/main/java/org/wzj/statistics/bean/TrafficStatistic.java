package org.wzj.statistics.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "流量统计信息")
public class TrafficStatistic {

    @Schema(description = "日期")
    private String dt;

    @Schema(description = "最近天数")
    private Integer recentDays;

    @Schema(description = "渠道")
    private String channel;

    @Schema(description = "UV数量")
    private Integer uvCount;

    @Schema(description = "平均停留时长（秒）")
    private Integer avgDurationSec;

    @Schema(description = "平均页面访问量")
    private Integer avgPageCount;

    @Schema(description = "SV数量")
    private Integer svCount;

    @Schema(description = "跳出率")
    private Double bounceRate;

}
