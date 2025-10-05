package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.dto.response.InformationResponse;
import com.work.service.dto.response.LogResponse;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.UserMapper;
import com.work.service.service.UserService;
import com.work.service.util.JwtUtil;
import com.work.service.util.RedisUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;
    private static final String userCache = "user";

    @Override
    @CacheEvict(value = userCache, key = "#userId")
    public void postAvatar(Integer userId, String avatarUrl) {
        User user = userMapper.selectById(userId);
        user.setPicture(avatarUrl);
        userMapper.updateById(user);
    }

    @Override
    public LogResponse login(Integer userId, String password) {
        User user = userMapper.selectById(userId);
        if(user==null){
           throw new ApiException(ExceptionEnum.NOT_FOUND_ERROR);
        }else {
            if (!user.getPassword().equals(password)) {
                throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
            }
        }
        String token = jwtUtil.generateToken(String.valueOf(userId));
        return new LogResponse(user.getUserType(), token);
    }

    @Override
    public Integer reg(String username, String password,String email) {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .userType(1)
                .build();
        userMapper.insert(user);
        return user.getUserId();
    }

    @Override
    @Cacheable(value = userCache, key = "#id")
    public void update(Integer id,String object,String content){
        User user=userMapper.selectById(id);
        if(user==null){
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }else{
            Map<String, BiConsumer<User,String>> updater =new HashMap<>();
            updater.put("username", User::setUsername);
            updater.put("password", User::setPassword);
            updater.put("email", User::setEmail);
            updater.put("sex", User::setSex);
            updater.put("picture", User::setPicture);
            updater.put("college", User::setCollege);
            updater.put("major", User::setMajor);
            updater.put("grade", User::setGrade);
            updater.put("phone", User::setPhone);
            if(updater.containsKey(object)){
                updater.get(object).accept(user,content);
                userMapper.updateById(user);
            }else{
                throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
            }
        }
    }

    @Override
    @Cacheable(value = userCache, key = "#id")
    public InformationResponse Information(Integer id){
        User user=userMapper.selectById(id);
        return InformationResponse.builder().userId(user.getUserId()).username(user.getUsername()).userType(user.getUserType()).sex(user.getSex()).email(user.getEmail()).college(user.getCollege()).major(user.getMajor()).grade(user.getGrade()).phone(user.getPhone()).build();
    }
}
