package com.github.liuxboy.mini.web.demo.service.impl;


import com.github.liuxboy.mini.web.demo.common.util.ENCODEUtil;
import com.github.liuxboy.mini.web.demo.dao.entity.UserEntity;
import com.github.liuxboy.mini.web.demo.dao.mapper.UserMapper;
import com.github.liuxboy.mini.web.demo.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(UserEntity entity) {
        //将密码进行MD5加密
        String pwd = ENCODEUtil.md5(entity.getPassWord());
        entity.setPassWord(pwd);
        return userMapper.insert(entity);
    }

    @Override
    public UserEntity login(UserEntity entity) {
        return entity;
        //Map<String, String> param = new HashMap<String, String>();
        //param.put("userName", entity.getUserName());
        //param.put("passWord", ENCODEUtil.md5(entity.getPassWord()));
        //param.put("passWord", entity.getPassWord());
        //return userMapper.queryByUserNameAndPassWord(param);
    }

    @Override
    public int delete(long id) {
        return userMapper.delete(id);
    }

    @Override
    public List<UserEntity> list() {
        return userMapper.list();
    }

    @Override
    public List<UserEntity> queryByParam(Map param) {
        return userMapper.queryByParam(param);
    }

    @Override
    public UserEntity get(long id) {
        return userMapper.get(id);
    }

    @Override
    public int update(UserEntity entity) {
        //将密码进行MD5加密
        if(!StringUtils.isBlank(entity.getPassWord())){
            String pwd = ENCODEUtil.md5(entity.getPassWord());
            entity.setPassWord(pwd);
        }
        return userMapper.updateUser(entity);
    }
}
