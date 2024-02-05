package org.wzj.statistics.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "分页信息")
public class Page<T> {

    @Schema(description = "总记录数")
    public Integer total;

    @Schema(description = "每页大小")
    public Integer size;

    @Schema(description = "当前页码")
    public Integer current;

    @Schema(description = "记录列表")
    public List<T> records;

}
