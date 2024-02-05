package org.wzj.statistics.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "页面路径")
public class PagePath {

    @Schema(description = "源页面")
    private String source;

    @Schema(description = "目标页面")
    private String target;

    @Schema(description = "路径值")
    private Integer value;

}
