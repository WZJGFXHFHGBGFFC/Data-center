package org.wzj.manager.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.wzj.manager.entity.User;
import java.util.List;

@Mapper
public interface IndexMapper {
    List<User> userRolesInfo(String username);

    //查询用户信息
    User login(String username);
}
