package org.wzj.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.entity.UserRole;
import org.wzj.manager.service.UserService;
import org.wzj.manager.entity.User;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;
import java.util.List;


@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户
     */
    @Operation(summary = "获取所有用户")
    @GetMapping("getAllUsers")
    public Result<List<User>> getAllUsers() {

        return Result.build(ResultCodeEnum.SUCCESS, userService.getAllUsers());
    }

    /**
     * 根据用户ID获取用户信息
     */
    @Operation(summary = "根据用户ID获取用户信息")
    @GetMapping("getUser/{userId}")
    public Result<User> getUserById(@PathVariable Long userId) {
        return Result.build(ResultCodeEnum.SUCCESS, userService.getUserById(userId));
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping("addUser")
    public Result<String> addUser(@RequestBody User userInfo) {
        userService.addUser(userInfo);
        return Result.build(ResultCodeEnum.SUCCESS, "新添加了一个用户");
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("deleteUser/{userId}")
    public Result<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return Result.build(ResultCodeEnum.SUCCESS, "删除用户完毕");
    }


    /**
     * 根据用户ID获取角色ID
     */
    @Operation(summary = "根据用户ID获取角色ID")
    @GetMapping("getRoleIdByUserId/{userId}")
    public Result<Long> getRoleIdByUserId(@PathVariable Long userId) {
        return Result.build(ResultCodeEnum.SUCCESS, userService.getRoleIdByUserId(userId));
    }

    /**
     * 为用户分配角色
     */
    @Operation(summary = "为用户分配角色")
    @PostMapping("assignRoleToUser")
    public Result<String> assignRoleToUser(@RequestBody UserRole userRole) {
        userService.assignRoleToUser(userRole);
        return Result.build(ResultCodeEnum.SUCCESS, "分配角色成功");
    }
}
