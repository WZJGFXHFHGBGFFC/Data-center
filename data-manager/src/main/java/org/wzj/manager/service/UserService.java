package org.wzj.manager.service;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserRole;

import java.util.List;

public interface UserService {
    /**
     * 获取所有用户信息
     */
    List<User> getAllUsers();

    /**
     * 根据用户ID获取用户信息
     */
    User getUserById(Long userId);

    /**
     * 根据用户ID删除用户及用户对应的角色信息
     */
    void deleteUserById(Long userId);

    /**
     * 添加新用户
     */
    void addUser(User userInfo);

    /**
     * 根据用户ID获取角色ID
     */
    List<Long> getRoleIdByUserId(Long userId);

    /**
     * 为用户分配角色
     */
    void assignRoleToUser(UserRole userRole);
}
