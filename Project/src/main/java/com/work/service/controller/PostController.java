package com.work.service.controller;

import com.work.service.Result.AjaxResult;
import com.work.service.dto.request.AcceptRequest;
import com.work.service.dto.request.CommentRequest;
import com.work.service.dto.request.PublishRequest;
import com.work.service.dto.request.ResponseRequest;
import com.work.service.entity.Post;
import com.work.service.service.PostService;
import com.work.service.util.CurrentUserId;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class PostController {
    @Resource
    private PostService postService;


    @PostMapping(value="/student/post")

    public AjaxResult<Void> publishPost(@Valid @RequestBody PublishRequest post
                                        , @CurrentUserId Integer userId) {
        postService.publish( userId, post.getTitle(), post.getContent(), post.getLevel(), post.getHide());
        return AjaxResult.success();
    }

    @GetMapping("/student/post")
    public AjaxResult<List<Post>> checkPost(@CurrentUserId Integer userId) {
        List<Post> posts = postService.check(userId);
        return AjaxResult.success(posts);
    }

    @PostMapping("/student/comment")
    public AjaxResult<Void> comment(@Valid @RequestBody CommentRequest comment
                                    ,@CurrentUserId Integer userId) {
        postService.comment(userId, comment.getPostId(), comment.getContent());
        return AjaxResult.success();
    }

    @GetMapping("/admin/getAllPosts")
    public AjaxResult<List<Post>> getPosts(@RequestParam Integer userId) {
        List<Post> posts =postService.getPosts(userId);
        return AjaxResult.success(posts);
    }

    @PostMapping("/admin/response")
    public AjaxResult<Void> response(@Valid @RequestBody ResponseRequest response,@CurrentUserId Integer userId) {
        postService.response(userId, response.getPostId(), response.getContent());
        return AjaxResult.success();
    }

    @PutMapping("/admin/acceptPost")
    public AjaxResult<Void> acceptPost(@Valid @RequestBody AcceptRequest accept,@CurrentUserId Integer userId) {
        postService.acceptPost(userId, accept.getPostId());
        return AjaxResult.success();
    }
    @GetMapping("/admin/select")
    public AjaxResult<List<Post>> getAcceptPosts(@CurrentUserId Integer userId) {
        List<Post> posts = postService.getAcceptPosts(userId);
        return AjaxResult.success(posts);
    }

    @PutMapping("/admin/delete_accept")
    public AjaxResult<Void> deleteAccept(@Valid @RequestBody AcceptRequest accept,@CurrentUserId Integer userId) {
        postService.deleteAccept(userId, accept.getPostId());
        return AjaxResult.success();
    }






}
