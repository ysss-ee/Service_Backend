package com.work.service.service;

import com.work.service.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    void acceptMessage(String adminName,Integer acceptUserId,Integer postId);
    void deleteAccept(Integer acceptUserId,Integer postId);
    void resolveMessage(Integer acceptUserId,Integer postId);
    void reportMessage(Integer acceptUserId,Integer postId);
    List<Message> getMessage(Integer acceptUserId);
}
