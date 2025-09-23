package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.UserMapper;
import com.work.service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Integer login(String userName, String password) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, userName);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
           throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
        }else{
            if(!user.getPassword().equals(password)) {
                throw new ApiException(ExceptionEnum.WRONG_USERNAME_OR_PASSWORD);
            }
        }
        return user.getUserType();
    }

    @Override
    public Integer reg(String userName, String password,String email) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, userName);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
            user=User.builder().username(userName).password(password).email(email).userType(1).build();
            userMapper.insert(user);
        }else{
                throw new ApiException(ExceptionEnum.ALREADY_EXISTS);
        }
        return user.getUserId();
    }

    @Override
    public void update(Integer id,String object,String content){
        User user=userMapper.selectById(id);
        if(user==null){
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }else{
            switch(object){
                case "username":
                    user.setUsername(content);
                    break;
                case "password":
                    user.setPassword(content);
                    break;
                case "email":
                    user.setEmail(content);
                    break;
                case "sex":
                    user.setSex(content);
                    break;
                case "picture":
                    user.setPicture(content);
                    break;
                case "college":
                    user.setCollege(content);
                    break;
                case "major":
                    user.setMajor(content);
                    break;
                case "grade":
                    user.setGrade(content);
                    break;
                case "phone":
                    user.setPhone(content);
                    break;
                default:
                    return;
            }
            userMapper.updateById(user);
        }
    }

    @Override
    public void manage(Integer id, Integer userType){
        User user=userMapper.selectById(id);
        if(user==null){
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }else{
            user.setUserType(userType);
        }
        userMapper.updateById(user);
    }
}
