package org.wzj.manager.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.bean.UserRole;
import org.wzj.manager.service.UserRoleService;
import org.wzj.manager.service.UserService;
import org.wzj.manager.util.MD5;
import org.wzj.manager.bean.User;
import org.wzj.model.util.Result;

import java.util.List;


@RestController
@RequestMapping("/admin/user")
@Tag(name = "登录模块")
public class UserController {
    /**
     * 获取所有用户
     */
    @Operation(summary = "获取所有用户")
    @GetMapping("getAllUsers")
    public Result<List<User>> getAllUsers() {
        return Result.of(200, "success", UserService.getAllUsers());
    }

    /**
     * 根据用户ID获取用户信息
     */
    @Operation(summary = "根据用户ID获取用户信息")
    @GetMapping("getUser/{userId}")
    public Result<User> getUserById(@PathVariable Long userId) {
        return Result.of(200, "success", UserService.getUserById(userId));
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping("addUser")
    public Result<String> addUser(@RequestBody User userInfo) {
        UserService.addUser(
                userInfo.getUsername(),
                MD5.encrypt(userInfo.getPassword())
        );
        return Result.of(200, "success", "新添加了一个用户");
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("deleteUser/{userId}")
    public Result<String> deleteUser(@PathVariable Long userId) {
        UserService.deleteUserById(userId);
        return Result.of(200, "success", "删除用户完毕");
    }

    /**
     * 根据用户ID获取角色ID
     */
    @Operation(summary = "根据用户ID获取角色ID")
    @GetMapping("getRoleIdByUserId/{userId}")
    public Result<Long> getRoleIdByUserId(@PathVariable Long userId) {
        return Result.of(200, "success", UserRoleService.getRoleIdByUserId(userId));
    }

    /**
     * 为用户分配角色
     */
    @Operation(summary = "为用户分配角色")
    @PostMapping("assignRoleToUser")
    public Result<String> assignRoleToUser(@RequestBody UserRole userRole) {
        UserRoleService.addUserIdRoleIdRelationship(userRole.getUserId(), userRole.getRoleId());
        return Result.of(200, "success", "分配角色成功");
    }
}
