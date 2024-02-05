package org.wzj.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.entity.Permission;
import org.wzj.manager.entity.RolePermission;
import org.wzj.manager.service.RoleService;
import java.util.List;
import org.wzj.manager.entity.Role;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;


@RestController
@RequestMapping("/admin/role")
@Tag(name = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色
     */
    @Operation(summary = "获取所有角色")
    @GetMapping("getAllRoles")
    public Result<List<Role>> getAllRoles() {
        return Result.build(ResultCodeEnum.SUCCESS, roleService.getAllRoles());
    }

    /**
     * 根据角色ID删除角色
     */
    @Operation(summary = "根据角色ID删除角色")
    @DeleteMapping("deleteRole/{roleId}")
    public Result<String> deleteRoleById(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return Result.build(ResultCodeEnum.SUCCESS, "角色删除成功");
    }

    /**
     * 添加新角色
     */
    @Operation(summary = "添加新角色")
    @PostMapping("addRole")
    public Result<String> addRole(@RequestParam String roleName) {
        roleService.addRole(roleName);
        return Result.build(ResultCodeEnum.SUCCESS, "角色添加成功");
    }

    /**
     * 根据角色ID获取菜单
     */
    @Operation(summary = "根据角色ID获取菜单")
    @GetMapping("permissions/{roleId}")
    public Result<List<Permission>> getPermissionsByRoleId(@PathVariable Long roleId) {
        return Result.build(ResultCodeEnum.SUCCESS, roleService.getPermissionsByRoleId(roleId));
    }

    /**
     * 为角色分配权限
     */
    @Operation(summary = "为角色分配权限")
    @PostMapping("assignPermissionsToRole")
    public Result<String> assignRoleToPermissions(@RequestBody RolePermission rolePermission) {
        roleService.assignRoleToPermissions(rolePermission);
        return Result.build(ResultCodeEnum.SUCCESS, "权限分配成功");
    }
}
