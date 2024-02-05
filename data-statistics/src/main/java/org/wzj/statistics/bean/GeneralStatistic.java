package org.wzj.statistics.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "通用统计信息")
public class GeneralStatistic {

    @Schema(description = "统计项名称")
    private String name;

    @Schema(description = "统计项值")
    private String value;

}
