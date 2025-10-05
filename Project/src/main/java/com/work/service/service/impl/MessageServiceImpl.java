package com.work.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.work.service.entity.Message;
import com.work.service.mapper.MessageMapper;
import com.work.service.service.MessageService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    @CacheEvict(value = "message", key = "#acceptUserId")
    public void acceptMessage(String adminName, Integer acceptUserId, Integer postId) {
        Message message = Message.builder()
                .acceptUserId(acceptUserId)
                .content("您的反馈" + postId + "已被管理员/{adminName}接单")
                .build();
        messageMapper.insert(message);
    }

    @Override
    @CacheEvict(value = "message", key = "#acceptUserId")
    public void deleteAccept(Integer acceptUserId, Integer postId) {
        Message message = Message.builder()
                .acceptUserId(acceptUserId)
                .content("您的反馈" + postId + "已被取消接单。")
                .build();
        messageMapper.insert(message);

    }

    @Override
    @CacheEvict(value = "message", key = "#acceptUserId")
    public void resolveMessage(Integer acceptUserId, Integer postId) {
        Message message = Message.builder()
                .acceptUserId(acceptUserId)
                .content("您的反馈" + postId + "已被管理员/{adminName}处理完成。")
                .build();
        messageMapper.insert( message);

    }

    @Override
    @CacheEvict(value = "message", key = "#acceptUserId")
    public void reportMessage(Integer acceptUserId, Integer postId) {
        Message message = Message.builder()
                .acceptUserId(acceptUserId)
                .content("您的反馈" + postId + "已被举报。请您在反馈时保证问题的有效性和准确性，感谢您的理解和配合。如有异议，请重新反馈。")
                .build();
        messageMapper.insert(message);

    }

    @Override
    @Cacheable(value = "message", key = "#userId")
    public List<Message> getMessage(Integer userId) {
        List<Message> messages = messageMapper.selectList(new LambdaQueryWrapper<Message>().eq(Message::getAcceptUserId, userId));
        return messages;
    }

}
