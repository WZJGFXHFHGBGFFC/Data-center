package org.wzj.manager.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserRole;
import org.wzj.manager.mapper.UserMapper;
import org.wzj.manager.service.UserService;
import org.wzj.manager.util.MD5;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户信息
     */
    @Override
    public List<User> getAllUsers() {return userMapper.getAllUsers();}

    /**
     * 根据用户ID获取用户信息
     */
    @Override
    public User getUserById(Long userId) {
        userMapper.getUserById(userId);
        return null;
    }

    /**
     * 根据用户ID删除用户及用户对应的角色信息
     */
    @Transactional
    @Override
    public void deleteUserById(Long userId) {
        //根据用户ID删除用户
        userMapper.deleteUserById(userId);
        //根据用户ID用户对应的角色信息
        userMapper.deleteRoleUserById(userId);
    }

    /**
     * 添加新用户
     */
    @Override
    public void addUser(User userInfo) {
        userMapper.addUser(userInfo.getUsername(), MD5.encrypt(userInfo.getPassword()));
    }

    /**
     * 根据用户ID获取角色ID
     */
    @Override
    public List<Long> getRoleIdByUserId(Long userId) {
        return userMapper.getRoleIdByUserId(userId);
    }

    /**
     * 为用户分配角色
     */
    @Transactional
    @Override
    public void assignRoleToUser(UserRole userRole) {
        
        //分配前先对该用户的所有角色进行删除
        userMapper.deleteRoleUserById(userRole.getUserId());
        
        //为用户分配角色
        userMapper.assignRoleToUser(userRole);
    }
}
