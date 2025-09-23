package com.work.service.controller;

import com.work.service.dto.Result;
import com.work.service.dto.request.LoginRequest;
import com.work.service.dto.request.ManageRequest;
import com.work.service.dto.request.RegRequest;
import com.work.service.dto.request.UpdateRequest;
import com.work.service.dto.response.LoginResponse;
import com.work.service.dto.response.RegResponse;
import com.work.service.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/reg")
    public Result<RegResponse> reg(@Valid @RequestBody RegRequest request) {
        Integer userId =userService.reg(request.getUsername(),request.getPassword(),request.getEmail());
        return Result.success(new RegResponse(userId));
    }
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        Integer type =userService.login(request.getUsername(), request.getPassword());
        return Result.success(new LoginResponse(type));
    }
    @PatchMapping("/update")
    public Result<String> update(@Valid @RequestBody UpdateRequest request){
        userService.update(request.getId(),request.getObject(),request.getContent());
        return Result.success("更新个人信息成功");
    }
    @PutMapping("/manage")
    public Result<String> manage(@Valid @RequestBody ManageRequest request){
        userService.manage(request.getUserId(), request.getUserType());
        return Result.success("管理账号类型成功");
    }
}
