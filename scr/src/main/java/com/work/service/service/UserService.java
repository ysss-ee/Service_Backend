package com.work.service.service;

public interface UserService {
    public Integer login(String username, String password);
    public Integer reg(String username, String password, String email);
    public void update(Integer id, String object, String content);
    public void manage(Integer id, Integer userType);
}
