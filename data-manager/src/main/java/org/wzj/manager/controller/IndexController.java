package org.wzj.manager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserLoginInfo;
import org.wzj.manager.service.IndexService;
import org.wzj.manager.util.JwtHelper;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;

@RestController
@RequestMapping("/admin/index")
@Tag(name = "登录模块")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 登录API
     */
    @Operation(summary = "登录API")
    @PostMapping("login")
    public Result<String> login(@RequestBody User userInfo) {return indexService.login(userInfo);}

    /**
     * 根据token获取用户权限信息
     */
    @Operation(summary = "根据token获取用户权限信息")
    @GetMapping("userInfo")
    public Result<UserLoginInfo> userRolesInfo(ServerHttpRequest request) {
        //从请求头获取token
        String token = request.getHeaders().getFirst("token");
        //使用工具类反编译token解析出用户名
        String userName = JwtHelper.getUserName(token);
        //查询该用户的路由权限
        UserLoginInfo userLoginInfo = indexService.userRolesInfo(userName);
        return Result.build(ResultCodeEnum.SUCCESS, userLoginInfo);
    }

    /**
     * 退出登录
     */
    @Operation(summary = "退出登录")
    @PostMapping("logout")
    public Result<Void> logout() {
        return Result.build(ResultCodeEnum.SUCCESS, "退出成功");
    }
}
