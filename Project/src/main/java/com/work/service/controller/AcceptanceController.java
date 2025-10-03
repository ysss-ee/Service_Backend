package com.work.service.controller;

import com.work.service.Result.AjaxResult;
import com.work.service.dto.request.AcceptRequest;
import com.work.service.dto.request.ResolveRequest;
import com.work.service.entity.Post;
import com.work.service.service.AcceptanceService;
import com.work.service.util.CurrentUserId;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class AcceptanceController {
    @Resource
    private AcceptanceService acceptanceService;


    @PutMapping("/admin/acceptPost")
    public AjaxResult<Void> acceptPost(@Valid @RequestBody AcceptRequest accept, @CurrentUserId Integer userId) {
        acceptanceService.acceptPost(userId, accept.getPostId());
        return AjaxResult.success();
    }
    @GetMapping("/admin/select")
    public AjaxResult<List<Post>> getAcceptPosts(@CurrentUserId Integer userId) {
        List<Post> posts = acceptanceService.getAcceptPosts(userId);
        return AjaxResult.success(posts);
    }

    @PutMapping("/admin/delete_accept")
    public AjaxResult<Void> deleteAccept(@Valid @RequestBody AcceptRequest accept,@CurrentUserId Integer userId) {
        acceptanceService.deleteAccept(userId, accept.getPostId());
        return AjaxResult.success();
    }
    @PutMapping("/admin/resolve")
    public AjaxResult<Void> resolvePost(@Valid @RequestParam Integer acceptanceId, @CurrentUserId Integer userId) {
        acceptanceService.resolvePost(userId,acceptanceId);
        return AjaxResult.success();
    }
}
