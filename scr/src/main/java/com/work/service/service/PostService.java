package com.work.service.service;
import com.work.service.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hp
 * @description 针对表【post】的数据库操作Service
 * @createDate 2025-08-21 14:16:31
 */
@Service
public interface PostService {
    void publish(Integer  userId, String title, String content, Integer level, Integer hide);
    List<Post> check(Integer userId);
    void comment(Integer userId, Integer postId, String comment);
    List<Post> getPosts(Integer userId);
    void response(Integer userId, Integer postId, String response);
    void acceptPost(Integer userId, Integer postId);
}
