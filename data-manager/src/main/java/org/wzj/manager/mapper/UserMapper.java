package org.wzj.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.manager.entity.User;
import org.wzj.manager.entity.UserRole;

import java.util.List;

@Mapper
public interface UserMapper {

    //获取所有用户信息
    List<User> getAllUsers();

    // 根据用户ID获取用户信息
    User getUserById(Long userId);

    void addUser(String username, String password);

    //根据用户ID删除用户
    void deleteUserById(Long userId);

    //根据用户ID用户对应的角色信息
    void deleteRoleUserById(Long userId);

    //根据用户ID获取角色ID
    List<Long> getRoleIdByUserId(Long userId);

    //为用户分配角色
    void assignRoleToUser(UserRole userRole);
}
