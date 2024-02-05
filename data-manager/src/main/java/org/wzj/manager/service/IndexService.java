package org.wzj.manager.service;


import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserLoginInfo;
import org.wzj.model.common.Result;

public interface IndexService {

    //登录API
    Result<String> login(User userInfo);

    //根据用户名称获取用户权限信息
    UserLoginInfo userRolesInfo(String username);
}
