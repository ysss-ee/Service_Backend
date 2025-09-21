package com.github.reaper6767.demoproject.service.impl;

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

    @Override
    public Integer login(String userName, String password) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getUsername, userName);
        User user=userMapper.selectOne(userQueryWrapper);
        if(user==null){
           return 0;
        }else{
            if(!user.getPassword().equals(password)) {
                return -1;
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
                return -1;
        }
        return user.getUserId();
    }

    @Override
    public void update(Integer id,String object,String content){
        User user=userMapper.selectById(id);
        if(user==null){
            return;
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
            return;
        }
    }

    @Override
    public void manage(Integer id, Integer userType){
        User user=userMapper.selectById(id);
        if(user==null){
            return;
        }else{
            user.setUserType(userType);
        }
        userMapper.updateById(user);
        return;
    }
}
