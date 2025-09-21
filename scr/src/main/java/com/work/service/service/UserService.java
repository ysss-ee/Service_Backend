package com.work.service.service;
import org.springframework.stereotype.Service;

/**
 * @author hp
 * @description 针对表【user】的数据库操作Service
 * @createDate 2025-08-21 23:08:10
 */
@Service
public interface UserService {
    void postAvatar(Integer userId, String avatarUrl);

}
