package com.work.service.controller;

import com.github.reaper6767.demoproject.dto.*;
import com.github.reaper6767.demoproject.result.AjaxResult;
import com.github.reaper6767.demoproject.service.UserService;
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
    public AjaxResult<RegResponse> reg(@Valid @RequestBody RegRequest request) {
        Integer userId =userService.reg(request.getUsername(),request.getPassword(),request.getEmail());
        return AjaxResult.success(new RegResponse(userId));
    }
    @PostMapping("/login")
    public AjaxResult<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        Integer type =userService.login(request.getUsername(), request.getPassword());
        return AjaxResult.success(new LoginResponse(type));
    }
    @PatchMapping("/update")
    public AjaxResult<String> update(@Valid @RequestBody UpdateRequest request){
        userService.update(request.getId(),request.getObject(),request.getContent());
        return AjaxResult.success("更新个人信息成功");
    }
    @PutMapping("/manage")
    public AjaxResult<String> manage(@Valid @RequestBody ManageRequest request){
        userService.manage(request.getUserId(), request.getUserType());
        return AjaxResult.success("管理账号类型成功");
    }
}
