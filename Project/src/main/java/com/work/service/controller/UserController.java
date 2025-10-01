package com.work.service.controller;

import com.work.service.Result.AjaxResult;
import com.work.service.dto.request.LoginRequest;
import com.work.service.dto.request.ManageRequest;
import com.work.service.dto.request.RegRequest;
import com.work.service.dto.request.UpdateRequest;
import com.work.service.dto.response.RegResponse;
import com.work.service.service.UserService;
import com.work.service.util.CurrentUserId;
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
    public AjaxResult<String> login(@Valid @RequestBody LoginRequest request){
        String token =userService.login(request.getUsername(), request.getPassword());
        return AjaxResult.success(token);
    }
    @PatchMapping("/update")
    public AjaxResult<String> update(@Valid @RequestBody UpdateRequest request,@CurrentUserId Integer userId){
        userService.update(userId,request.getObject(),request.getContent());
        return AjaxResult.success("更新个人信息成功");
    }
    @PutMapping("/manage")
    public AjaxResult<String> manage(@Valid @RequestBody ManageRequest request,@CurrentUserId Integer userId){
        userService.manage(userId, request.getUserType());
        return AjaxResult.success("管理账号类型成功");
    }
}
