package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.entity.Acceptance;
import com.work.service.entity.Post;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.AcceptanceMapper;
import com.work.service.mapper.PostMapper;
import com.work.service.mapper.UserMapper;
import com.work.service.service.AcceptanceService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcceptanceServiceImpl implements AcceptanceService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private AcceptanceMapper acceptanceMapper;
    @Resource
    private MessageServiceImpl messageService;

    /**
     * 检查帖子是否存在
     */
    private Post getPostIfExists(Integer postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        return post;
    }

    /**
     * 检查权限
     */
    private String checkPermission(Integer userId) {
        User user = userMapper.selectById(userId);
        Integer type = user.getUserType();
        if (type != 2 && type != 3) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }
        return user.getUsername();

    }

    /**
     * 接单
     * 修改post的状态
     * 创建acceptance
     * 发送消息
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "acceptPosts", key = "#userId"),
            @CacheEvict(value = "post", allEntries = true)})
    public void acceptPost(Integer userId, Integer postId) {
        String adminName = checkPermission(userId);

        Post post = getPostIfExists(postId);
        post.setState(2);
        post.setAcceptUserId(userId);
        postMapper.updateById(post);

        Acceptance acceptance = Acceptance.builder()
                .userId(userId)
                .postId(postId)
                .state(1)
                .build();
        acceptanceMapper.insert(acceptance);

        messageService.acceptMessage(adminName, userId, postId);
    }

    /**
     * 获取接单的帖子
     */
    @Override
    @Cacheable(value = "acceptPosts", key = "#userId")
    public List<Post> getAcceptPosts(Integer userId) {
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>().eq(Post::getAcceptUserId, userId));
        for (Post post : posts) {
            if (post.getHide() == 1) {
                post.setUserId(-1);
            }
        }
        return posts;
    }

    /**
     * 取消接单
     * 修改post的状态
     * 软删除acceptance
     * 发送消息
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "acceptPosts", key = "#userId"),
            @CacheEvict(value = "post", allEntries = true)})
    public void deleteAccept(Integer userId, Integer acceptanceId) {
        Acceptance acceptance = acceptanceMapper.selectById(acceptanceId);
        Integer postId = acceptance.getPostId();
        Post post = getPostIfExists(postId);
        post.setState(1);
        post.setAcceptUserId(0);
        postMapper.updateById(post);
        acceptance.setState(3);
        acceptance.setDeleteTime(new java.sql.Date(System.currentTimeMillis()));
        acceptanceMapper.updateById(acceptance);

        messageService.deleteAccept(acceptance.getUserId(), postId);
    }

    /**
     * 处理完成
     * 修改post的状态
     * 修改acceptance的状态
     * 发送消息
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "acceptPosts", key = "#userId"),
            @CacheEvict(value = "post", allEntries = true)})
    public void resolvePost(Integer userId, Integer acceptanceId) {
        Acceptance acceptance = acceptanceMapper.selectById(acceptanceId);
        Integer postId = acceptance.getPostId();
        Post post = postMapper.selectById(postId);
        post.setState(3);
        postMapper.updateById(post);


        acceptance.setState(2);
        acceptanceMapper.updateById(acceptance);

        messageService.resolveMessage(post.getUserId(), postId);
    }
}
