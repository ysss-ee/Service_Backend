package com.work.service.controller;

import com.work.service.Result.AjaxResult;
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
    public AjaxResult<Void> acceptPost(@Valid @RequestParam Integer postId, @CurrentUserId Integer userId) {
        acceptanceService.acceptPost(userId, postId);
        return AjaxResult.success();
    }
    @GetMapping("/admin/select")
    public AjaxResult<List<Post>> getAcceptPosts(@CurrentUserId Integer userId) {
        List<Post> posts = acceptanceService.getAcceptPosts(userId);
        return AjaxResult.success(posts);
    }

    @PutMapping("/admin/delete_accept")
    public AjaxResult<Void> deleteAccept(@Valid @RequestParam Integer acceptanceId,@CurrentUserId Integer userId) {
        acceptanceService.deleteAccept(userId, acceptanceId);
        return AjaxResult.success();
    }
    @PutMapping("/admin/resolve")
    public AjaxResult<Void> resolvePost(@Valid @RequestParam Integer acceptanceId, @CurrentUserId Integer userId) {
        acceptanceService.resolvePost(userId,acceptanceId);
        return AjaxResult.success();
    }
}
