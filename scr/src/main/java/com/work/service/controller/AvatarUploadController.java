package com.work.service.controller;

import com.work.service.service.AvatarUploadService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class AvatarUploadController {

    @Resource
    private AvatarUploadService avatarUploadService;

    @PostMapping("/uploadAvatar")
    public ResponseEntity<Map<String, Object>> uploadAvatar(@RequestParam("avatar") MultipartFile file,
                    @RequestParam("userId") String userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 保存头像文件
            String fileName = avatarUploadService.saveAvatar(file, userId);

            // 获取头像URL
            String avatarUrl = avatarUploadService.getAvatarUrl(userId, fileName);

            // 在实际应用中，这里应该将avatarUrl保存到用户数据库中
            // userService.updateUserAvatar(userId, avatarUrl);
            response.put("success", true);
            response.put("message", "头像上传成功");
            response.put("avatarUrl", avatarUrl);
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e) {
            response.put("success", false);
            response.put("message", "上传失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
