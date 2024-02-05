package org.wzj.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserLoginInfo;
import org.wzj.manager.mapper.IndexMapper;
import org.wzj.manager.service.IndexService;
import org.wzj.manager.service.UserService;
import org.wzj.manager.util.JwtHelper;
import org.wzj.manager.util.MD5;
import org.wzj.model.common.Result;
import org.wzj.model.common.ResultCodeEnum;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private IndexMapper indexMapper;

    //登录API
    @Override
    public Result<String> login(User userInfo) {
        User user = indexMapper.login(userInfo.getUsername());

        if (user == null || !MD5.encrypt(userInfo.getPassword()).equals(user.getPassword())) {
            return Result.build(ResultCodeEnum.LOGIN_ERROR, "用户名或者密码错误");
        }

        var token = JwtHelper.createToken(user.getId(), user.getUsername());
        return Result.build(ResultCodeEnum.SUCCESS, token);
    }

    //根据用户名称获取用户权限信息
    @Override
    public UserLoginInfo userRolesInfo(String username) {
        //查询该用户的所有权限列表
        List<User> userList = indexMapper.userRolesInfo(username);
        //提取所有权限
        Set<String> roleNames = userList.stream().map(User::getPermissionCode).collect(Collectors.toSet());
        //构造UserLoginInfo返回值
        UserLoginInfo userLoginInfo = new UserLoginInfo(username,roleNames);
        return userLoginInfo;
    }
}
