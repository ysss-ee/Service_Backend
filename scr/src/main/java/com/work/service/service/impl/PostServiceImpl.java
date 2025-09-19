package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.entity.Post;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.PostMapper;
import com.work.service.mapper.UserMapper;
import com.work.service.service.PostService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hp
 * &#064;description  针对表【post】的数据库操作Service实现
 * &#064;createDate  2025-08-21 14:16:31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;
    @Resource
    private UserMapper userMapper;

    // 检查帖子是否存在
    private Post getPostIfExists(Integer postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        return post;
    }
    //检查权限
    private void checkPermission(Integer userId) {
        User user = userMapper.selectById(userId);
        if (user.getUserType() != 2&&user.getUserType() != 3) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }
    }


    @Override
    public void publish(Integer userId, String title, String content, Integer level, Integer hide) {
        Post post = Post.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .level(level)
                .hide(hide)
                .build();
        postMapper.insert(post);
    }

    @Override
    public List<Post> check(Integer userId) {
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>().eq(Post::getUserId, userId));
        if (posts.isEmpty() && userMapper.selectById(userId) == null) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        return posts;
    }

    @Override
    public void comment(Integer userId, Integer postId, String comment) {
        Post post = getPostIfExists(postId);
        String oldComment = post.getComment();
        post.setComment( userId+":"+comment+"\n"+oldComment);
        postMapper.updateById(post);
    }

    @Override
    public List<Post> getPosts(Integer userId) {
        checkPermission(userId);
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Post::getLevel, 0).ne(Post::getState, 2);
        queryWrapper.orderByAsc(Post::getLevel);
        List<Post> posts = postMapper.selectList(queryWrapper);
        for (Post post : posts) {
            if (post.getHide() == 1) {
                post.setUserId(-1);
            }
        }
        return posts;
    }

    @Override
    public void response(Integer userId, Integer postId, String response) {
        checkPermission(userId);
        Post post = getPostIfExists(postId);
        String oldResponse = post.getResponse();
        post.setResponse(userId+":"+response+"\n"+oldResponse);
        postMapper.updateById(post);
    }

    @Override
    public void acceptPost(Integer userId, Integer postId) {
        checkPermission(userId);
        Post post = getPostIfExists(postId);
        post.setState(2);
        post.setAcceptUserId(userId);
        postMapper.updateById(post);

    }
    @Override
    public List<Post> getAcceptPosts(Integer userId) {
        checkPermission(userId);
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>().eq(Post::getAcceptUserId, userId));
        for (Post post : posts) {
            if (post.getHide() == 1) {
                post.setUserId(-1);
            }
        }
        return posts;
    }

}
