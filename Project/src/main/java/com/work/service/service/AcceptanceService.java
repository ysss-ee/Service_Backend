package com.work.service.service;

import com.work.service.entity.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AcceptanceService {
    void acceptPost(Integer userId, Integer postId);

    List<Post> getAcceptPosts(Integer userId);

    void deleteAccept(Integer userId, Integer acceptanceId);

    void resolvePost(Integer userId, Integer acceptanceId);

}
