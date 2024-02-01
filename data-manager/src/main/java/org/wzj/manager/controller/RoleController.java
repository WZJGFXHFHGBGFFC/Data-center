package org.wzj.manager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.bean.Permission;
import org.wzj.manager.bean.RolePermission;
import org.wzj.manager.service.RolePermissionService;
import org.wzj.manager.service.RoleService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.wzj.manager.bean.Role;
import org.wzj.model.util.Result;

@RestController
@RequestMapping("/admin/role")
@Tag(name = "角色菜单")
public class RoleController {
    /**
     * 获取所有角色
     */
    @Operation(summary = "获取所有角色")
    @GetMapping("getAllRoles")
    public Result<List<Role>> getAllRoles() {
        return Result.of(200, "success", RoleService.getAllRoles());
    }

    /**
     * 根据角色ID删除角色
     */
    @Operation(summary = "根据角色ID删除角色")
    @DeleteMapping("deleteRole/{roleId}")
    public Result<String> deleteRoleById(@PathVariable Long roleId) {
        RoleService.deleteRole(roleId);
        return Result.of(200, "success", "删除角色成功");
    }

    /**
     * 添加新角色
     */
    @Operation(summary = "添加新角色")
    @PostMapping("addRole")
    public Result<String> addRole(@RequestParam String roleName) {
        RoleService.addRole(roleName);
        return Result.of(200, "success", "添加新角色成功");
    }

    /**
     * 根据角色ID获取菜单
     */
    @Operation(summary = "根据角色ID获取菜单")
    @GetMapping("permissions/{roleId}")
    public Result<List<Permission>> getPermissionsByRoleId(@PathVariable Long roleId) {
        return Result.of(200, "success", RolePermissionService.getPermissionsByRoleId(roleId));
    }

    /**
     * 为角色分配权限
     */
    @Operation(summary = "为角色分配权限")
    @PostMapping("assignPermissionsToRole")
    public Result<String> assignPermissionsToRole(@RequestBody RolePermission rolePermission) {
        RolePermissionService.addRolePermissions(
                rolePermission.getRoleId(),
                Arrays.stream(rolePermission.getPermissionIds().split(",")).map(Long::parseLong).collect(Collectors.toList())
        );
        return Result.of(200, "success", "分配成功");
    }
}
