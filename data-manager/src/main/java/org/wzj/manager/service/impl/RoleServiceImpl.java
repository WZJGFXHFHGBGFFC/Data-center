package org.wzj.manager.service.impl;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wzj.manager.entity.Permission;
import org.wzj.manager.entity.Role;
import org.wzj.manager.entity.RolePermission;
import org.wzj.manager.mapper.RoleMapper;
import org.wzj.manager.service.RoleService;
import org.wzj.manager.util.PermissionHelper;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取所有角色
     */
    @Override
    public List<Role> getAllRoles() {
        List<Role> roleList = roleMapper.getAllRoles();
        return roleList;
    }

    /**
     * 删除角色
     */
    @Transactional
    @Override
    public void deleteRole(Long roleId) {
        //删除角色本身
        roleMapper.deleteRole(roleId);

        //删除角色对应的权限
        roleMapper.deletePermissionsByRoleId(roleId);

        //删除角色对应的用户
        roleMapper.deleteUsersByRoleId(roleId);
    }

    /**
     * 根据角色ID获取菜单
     */
    @Override
    public List<Permission> getPermissionsByRoleId(Long roleId) {
        List<Permission> permissionList = roleMapper.getPermissionsByRoleId(roleId);
        //对角色拥有的权限进行标选为true
        permissionList.forEach(permission -> {
            if (permission.getPermissionId() != null) {
                permission.setSelect(true);
            }
        });
        //构造树形结构并返回
        return PermissionHelper.build(permissionList);
    }

    /**
     * 为角色分配权限
     */
    @Transactional
    @Override
    public void assignRoleToPermissions(RolePermission rolePermission) {
        //获取角色id
        Long roleId = rolePermission.getRoleId();

        //分配前先对该角色的所有权限进行删除
        roleMapper.deletePermissionsByRoleId(roleId);

        /**
         * rolePermission.getPermissionIds()的返回值如下示例,是Permission表的id集合
         * 1,2,7,8,3,9,10,4,12,13,5,14,6,15,16,17
         */
        //获得用户需要分配的权限列表id
        List<Long> permissionIds = Arrays.stream(rolePermission.getPermissionIds().split(",")).map(Long::parseLong).collect(Collectors.toList());

        //为角色分配权限
        if(permissionIds != null && permissionIds.size() > 0) {
            roleMapper.assignRoleToPermissions(roleId,permissionIds);
        }

    }


    /**
     * 添加新角色
     */
    public void addRole(String roleName) {
        roleMapper.addRole(roleName);
    }

}
