package com.github.liuxboy.mini.web.demo.dao.mapper;



import com.github.liuxboy.mini.web.demo.dao.entity.UserEntity;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    int insert(UserEntity entity);

    int updateUser(UserEntity entity);

    int delete(long id);

    UserEntity get(long id);

    List<UserEntity> list();

    List<UserEntity> queryByParam(Map entity);

    UserEntity queryByUserNameAndPassWord(Map entity);
}
