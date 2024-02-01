package org.wzj.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.bean.User;
import org.wzj.manager.bean.UserLoginInfo;
import org.wzj.manager.service.IndexService;
import org.wzj.manager.service.UserService;
import org.wzj.manager.util.JwtHelper;
import org.wzj.manager.util.MD5;
import org.wzj.model.util.Result;
@RestController
@RequestMapping("/admin/index")
@Tag(name = "登录")
public class IndexController {
    /**
     * 登录API
     */
    @Operation(summary = "登录API")
    @PostMapping("login")
    public Result<String> login(@RequestBody User userInfo) {
        var user = UserService.getUserByName(userInfo.getUsername());
        System.out.println(userInfo);
        if (user != null) {
            if (!MD5.encrypt(userInfo.getPassword()).equals(user.getPassword())) {
                return Result.of(200, "fail", "密码错误");
            } else {
                var token = JwtHelper.createToken(user.getId(), user.getUsername());
                return Result.of(200, "success", token);
            }
        } else {
            return Result.of(200, "fail", "用户不存在");
        }
    }

    /**
     * 根据token获取用户信息
     */

    @Operation(summary = "根据token获取用户信息")
    @GetMapping("userInfo")
    public Result<UserLoginInfo> userInfo(ServerHttpRequest request) {
        var token = request.getHeaders().getFirst("token");
        var username = JwtHelper.getUserName(token);
        var userLoginInfo = IndexService.getUserInfo(username);
        return Result.of(200, "success", userLoginInfo);
    }

//    @Operation(summary = "根据token获取用户信息")
//    @GetMapping("userInfo")
//    public Result<UserLoginInfo> userInfo(HttpServletRequest request) {
//        var token = request.getHeader("token");
//        var username = JwtHelper.getUserName(token);
//        var userLoginInfo = IndexService.getUserInfo(username);
//        return Result.of(200, "success", userLoginInfo);
//    }



    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @PostMapping("logout")
    public Result<Void> logout() {
        return Result.of(200, "success", null);
    }
}
