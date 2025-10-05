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
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public void postAvatar(Integer userId, String avatarUrl) {
        User user = userMapper.selectById(userId);
        user.setPicture(avatarUrl);
        userMapper.updateById(user);
    }

    @Override
    public LogResponse login(String userName, String password) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, userName);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
           throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
        }else {
            if (!user.getPassword().equals(password)) {
                throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
            }
        }
        String token = jwtUtil.generateToken(Long.valueOf(user.getUserId()));
        return new LogResponse(user.getUserType(), token);
    }

    @Override
    public Integer reg(String userName, String password,String email) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, userName);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null && userName.matches("\\w{2,20}") && (password.matches("[a-zA-Z0-9]{8,20}")) && email.matches("\\w+@\\w+\\.com")){
            user=User.builder().username(userName).password(password).email(email).userType(1).build();
            userMapper.insert(user);
            return user.getUserId();
        }else{
                throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
        }
    }

    @Override
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
    public InformationResponse Information(Integer id){
        User user=userMapper.selectById(id);
        return InformationResponse.builder().userId(user.getUserId()).username(user.getUsername()).userType(user.getUserType()).sex(user.getSex()).email(user.getEmail()).college(user.getCollege()).major(user.getMajor()).grade(user.getGrade()).phone(user.getPhone()).build();
    }
}
