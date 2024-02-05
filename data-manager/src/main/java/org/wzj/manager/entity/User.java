package org.wzj.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "用户信息")
public class User {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password; // 注意：在实际API文档中展示密码字段可能不安全，应考虑在相关API输出中忽略此字段

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "权限代码")
    private String permissionCode;


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", roleName='" + roleName + '\'' +
                ", permissionCode='" + permissionCode + '\'' +
                '}';
    }
}
