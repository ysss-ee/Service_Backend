package com.work.service.service;
import com.work.service.dto.response.InformationResponse;
import com.work.service.dto.response.LogResponse;
import org.springframework.stereotype.Service;

/**
 * @author hp
 * @description 针对表【user】的数据库操作Service
 * @createDate 2025-08-21 23:08:10
 */
@Service
public interface UserService {
    LogResponse login(String username, String password);
    Integer reg(String username, String password, String email);
    void update(Integer id, String object, String content);
    void postAvatar(Integer userId, String avatarUrl);
    InformationResponse Information(Integer id);
}
