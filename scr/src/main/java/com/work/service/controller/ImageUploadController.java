package com.work.service.controller;

import com.work.service.Result.AjaxResult;
import com.work.service.constant.ExceptionEnum;
import com.work.service.service.ImageUploadService;
import com.work.service.service.PostService;
import com.work.service.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class ImageUploadController {

    @Resource
    private ImageUploadService imageUploadService;
    @Resource
    private UserService userService;
    @Autowired
    private PostService postService;

    /**
     * 上传头像
     * @param file 头像文件
     * @param userId 用户ID
     * @return 头像URL
     */
    @PostMapping("/uploadAvatar")
    public AjaxResult uploadAvatar(@RequestParam("avatar") MultipartFile file,
                                   @RequestParam("userId") Integer userId) {
        try {
            // 保存头像文件
            String fileName = imageUploadService.saveAvatar(file,"avatar", String.valueOf(userId));
            // 获取头像URL
            String avatarUrl = imageUploadService.getUrl(userId,"avatar", fileName);

            userService.postAvatar(userId, avatarUrl);
            return AjaxResult.success(avatarUrl);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(ExceptionEnum.valueOf(e.getMessage()));
        } catch (Exception e) {
            return AjaxResult.error(500, "上传失败: " + e.getMessage());
        }
    }
    /**
     * 上传帖子图片
     * @param file 图片文件
     * @param postId 帖子ID
     * @return 图片URL
     */
    @PostMapping("/post/uploadImage")
    public AjaxResult uploadImage(@RequestParam("image") MultipartFile file,
                                  @RequestParam("postId") Integer postId) {
        try {
            // 保存头像文件
            String fileName = imageUploadService.saveAvatar(file,"post", String.valueOf(postId));
            // 获取头像URL
            String imageUrl = imageUploadService.getUrl(postId,"post", fileName);
            postService.postImage(postId, imageUrl);
            return AjaxResult.success(imageUrl);
        } catch (IllegalArgumentException e) {
            return AjaxResult.error(ExceptionEnum.valueOf(e.getMessage()));
        } catch (Exception e) {
            return AjaxResult.error(500, "上传失败: " + e.getMessage());
        }
    }
}
