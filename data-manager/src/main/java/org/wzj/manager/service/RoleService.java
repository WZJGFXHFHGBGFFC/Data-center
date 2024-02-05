package org.wzj.manager.service;


import org.wzj.manager.entity.Permission;
import org.wzj.manager.entity.Role;
import org.wzj.manager.entity.RolePermission;
import java.util.List;


public interface RoleService {


    /**
     * 获取所有角色
     */
    List<Role> getAllRoles();

    /**
     * 删除角色
     */
    void deleteRole(Long roleId);

    /**
     * 添加新角色
     */
    void addRole(String addRole);

    /**
     * 根据角色ID获取菜单
     */
    List<Permission> getPermissionsByRoleId(Long roleId);

    /**
     * 为角色分配权限
     */
    void assignRoleToPermissions(RolePermission rolePermission);
}
