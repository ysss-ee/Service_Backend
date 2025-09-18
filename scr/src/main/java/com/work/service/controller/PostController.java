package com.work.service.controller;

import com.work.service.dto.Result;
import com.work.service.dto.request.AcceptRequest;
import com.work.service.dto.request.CommentRequest;
import com.work.service.dto.request.PublishRequest;
import com.work.service.dto.request.ResponseRequest;
import com.work.service.entity.Post;
import com.work.service.service.PostService;
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

    @PostMapping("/student/post")
    public Result<Void> publishPost(@Valid @RequestBody PublishRequest post) {
        postService.publish( post.getUserId(), post.getTitle(), post.getContent(), post.getLevel(), post.getHide());
        return Result.success();
    }

    @GetMapping("/student/post")
    public Result<List<Post>> checkPost(@RequestParam Integer userId) {
        List<Post> posts = postService.check(userId);
        return Result.success(posts);
    }

    @PostMapping("/student/comment")
    public Result<Void> comment(@Valid @RequestBody CommentRequest comment) {
        postService.comment(comment.getUserId(), comment.getPostId(), comment.getContent());
        return Result.success();
    }

    @GetMapping("/admin/getAllPosts")
    public Result<List<Post>> getPosts(@RequestParam Integer userId) {
        List<Post> posts =postService.getPosts(userId);
        return Result.success(posts);
    }

    @PostMapping("/admin/response")
    public Result<Void> response(@Valid @RequestBody ResponseRequest response) {
        postService.response(response.getUserId(), response.getPostId(), response.getContent());
        return Result.success();
    }

    @PutMapping("/admin/acceptPost")
    public Result<Void> acceptPost(@Valid @RequestBody AcceptRequest accept) {
        postService.acceptPost(accept.getUserId(), accept.getPostId());
        return Result.success();
    }






}
