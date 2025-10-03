package com.work.service.controller;

import com.work.service.entity.Message;
import com.work.service.service.MessageService;
import com.work.service.util.CurrentUserId;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class MessageController {
    @Resource
    private MessageService messageService;

    @GetMapping("/student/getMessage")
    public List<Message> getMessage(@CurrentUserId Integer userId) {
        return messageService.getMessage(userId);
    }

}
