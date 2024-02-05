package org.wzj.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "权限信息")
public class Permission {

    @Schema(description = "权限ID")
    private Long id;

    @Schema(description = "父级权限ID")
    private Long parentId;

    @Schema(description = "权限名称")
    private String permissionName;

    @Schema(description = "权限代码")
    private String permissionCode;

    @Schema(description = "权限标识ID")
    private Long permissionId;

    @Schema(description = "权限等级")
    private Integer level;

    @Schema(description = "子权限列表")
    private List<Permission> children;

    @Schema(description = "是否选中")
    private Boolean select = false;
}
