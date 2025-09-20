package com.work.service.service.impl;

import com.work.service.service.AvatarUploadService;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarUploadServiceImpl implements AvatarUploadService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private static final String[] ALLOWED_CONTENT_TYPES = {
            "image/jpeg", "image/png", "image/gif", "image/jpg"
    };

    /**
     * 总逻辑
     */
    @Override
    public  String saveAvatar(MultipartFile file, String userId) throws IOException {
        validateFile(file);
        Path userDir = Paths.get(uploadDir, "avatars", userId);

        //生产唯一文件名
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID() + "." + fileExtension;

        Path filePath = userDir.resolve(uniqueFileName);
        processAndSaveImage(file, filePath);
        return uniqueFileName;
    }
    /**
     * 验证文件
     */
    private void validateFile(MultipartFile file){
        if (file.isEmpty()) {
            throw new IllegalArgumentException("请选择要上传的文件");
        }

        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new IllegalArgumentException("不支持的文件类型: " + contentType + "，请上传JPEG、PNG或GIF格式的图片");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("文件大小不能超过2MB");
        }
    }


    /**
     * 处理并保存图片（调整大小、转换格式等）
     */

    @Override
    public void processAndSaveImage(MultipartFile file, Path filePath) throws IOException {
        try {
            Thumbnails.of(file.getInputStream())
                    .size(200, 200)
                    .outputFormat(getFormatName(file.getOriginalFilename()).toLowerCase())
                    .toFile(filePath.toFile());
        } catch (IOException e) {
            throw new IOException("图片处理失败: " + e.getMessage(), e);
        }
    }


    /**
     * 检查内容类型是否允许
     */
    @Override
    public boolean isAllowedContentType(String contentType) {
        return Arrays.stream(ALLOWED_CONTENT_TYPES)
                .anyMatch(allowed -> allowed.equalsIgnoreCase(contentType));
    }


    /**
     * 获取文件扩展名
     */
    @Override
    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "jpg";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取图片格式名
     */
    @Override
    public String getFormatName(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        return switch (extension) {
            case "png" -> "PNG";
            case "gif" -> "GIF";
            default -> "JPEG"; // 包括jpg和jpeg
        };
    }


    /**
     * 获取头像的URL路径
     */
    @Override
    public String getAvatarUrl(String userId, String fileName) {
        return "/avatars/" + userId + "/" + fileName;
    }
}
