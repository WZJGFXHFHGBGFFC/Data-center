package org.wzj.manager.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户有权限访问的路由")
    private Set<String> routes = new HashSet<>();

}
