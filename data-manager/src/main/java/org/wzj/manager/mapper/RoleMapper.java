package org.wzj.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.manager.entity.Permission;
import org.wzj.manager.entity.Role;
import java.util.List;

@Mapper
public interface RoleMapper {

    //取所有角色
    List<Role> getAllRoles();

    //删除角色本身
    void deleteRole(Long roleId);

    //删除角色对应的权限
    void deletePermissionsByRoleId(Long roleId);

    //删除角色对应的用户
    void deleteUsersByRoleId(Long roleId);

    //添加新角色
    void addRole(String addRole);

    //根据角色ID获取菜单
    List<Permission> getPermissionsByRoleId(Long roleId);

    //为角色分配权限
    void assignRoleToPermissions(Long roleId,List<Long> permissionIds);
}
