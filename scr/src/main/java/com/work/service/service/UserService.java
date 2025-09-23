package com.work.service.service;

public interface UserService {
    Integer login(String username, String password);
    Integer reg(String username, String password, String email);
    void update(Integer id, String object, String content);
    void manage(Integer id, Integer userType);
}
