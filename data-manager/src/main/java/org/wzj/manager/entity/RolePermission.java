package org.wzj.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "角色权限关联信息")
public class RolePermission {

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "权限ID集合")
    private String permissionIds; // 注意：这里使用String类型来表示权限ID集合，可能需要特定的格式，例如逗号分隔的字符串

}
