package com.work.service.service.impl;

import com.work.service.entity.User;
import com.work.service.mapper.UserMapper;
import com.work.service.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public void postAvatar(Integer userId, String avatarUrl) {
        User user = userMapper.selectById(userId);
        user.setPicture(avatarUrl);
        userMapper.updateById(user);
    }
}
