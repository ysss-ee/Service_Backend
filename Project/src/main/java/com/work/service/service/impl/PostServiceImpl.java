package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.constant.ExceptionEnum;
import com.work.service.entity.Post;
import com.work.service.entity.Response;
import com.work.service.entity.User;
import com.work.service.exception.ApiException;
import com.work.service.mapper.PostMapper;
import com.work.service.mapper.ResponseMapper;
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
    @Resource
    private ResponseMapper responseMapper;


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
    private void checkPermission(Integer userId) {
        User user = userMapper.selectById(userId);
        Integer type = user.getUserType();
        if (type != 2&&type!= 3) {
            throw new ApiException(ExceptionEnum.PERMISSION_NOT_ALLOWED);
        }
    }

    /**
     * 发布帖子
     */
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

    /**
      帖子上传图片
     */
    @Override
    public void postImage(Integer postId, String imageUrl) {
        Post post = postMapper.selectById(postId);
        post.setImage(imageUrl);
        postMapper.updateById( post);
    }
    /**
     * 查看自己帖子
     */
    @Override
    public List<Post> check(Integer userId) {
        List<Post> posts = postMapper.selectList(new LambdaQueryWrapper<Post>().eq(Post::getUserId, userId));
        if (posts.isEmpty()) {
            throw new ApiException(ExceptionEnum.RESOURCE_NOT_FOUND);
        }
        return posts;
    }
    /**
     * 评论帖子
     */
    @Override
    public void comment(Integer userId, Integer postId, String comment) {
        Post post = getPostIfExists(postId);
        post.setComment( comment);
        postMapper.updateById(post);
    }
    /**
     * 获取帖子
     */
    @Override
    public List<Post> getPosts(Integer userId) {
        checkPermission(userId);
        Integer type = userMapper.selectById(userId).getUserType();
        LambdaQueryWrapper<Post> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(Post::getLevel, 0);
        if(type == 2) {
            queryWrapper.ne(Post::getState, 2);
        }
        queryWrapper.orderByAsc(Post::getLevel);
        List<Post> posts = postMapper.selectList(queryWrapper);
        for (Post post : posts) {
            List<Response> response = responseMapper.selectList(
                    new LambdaQueryWrapper<Response>().eq(Response::getPostId, post.getPostId())
            );
            post.setResponse(response);
            if (post.getHide() == 1) post.setUserId(-1);
        }
        return posts;
    }
    /**
     * 管理员回复帖子
     */
    @Override
    public void response(Integer userId, Integer postId, String content) {
        checkPermission(userId);
        Response response = Response.builder()
                .postId(postId)
                .content(content)
                .build();
        responseMapper.insert(response);
    }

}
