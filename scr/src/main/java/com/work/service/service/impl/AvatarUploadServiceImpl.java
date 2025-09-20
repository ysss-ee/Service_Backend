package com.work.service.service.impl;

import com.work.service.service.AvatarUploadService;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvatarUploadServiceImpl implements AvatarUploadService {

    @Value("{file.upload.dir}")
    private String uploadDir;

    // 允许的文件类型
    private static final String[] ALLOWED_CONTENT_TYPES = {
            "image/jpeg", "image/png", "image/gif", "image/jpg"
    };


    /**
     * 保存用户头像
     *
     @param
     file 上传的文件
     *
     @param
     userId 用户ID
     *
     @return
     保存后的文件名
     *
     @throws
     IOException 如果文件操作失败
     *
     @throws
     IllegalArgumentException 如果文件不符合要求
     */
    @Override
    public String saveAvatar(MultipartFile file, String userId) throws IOException {
        // 验证文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("请选择要上传的文件");
        }

        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedContentType(contentType)) {
            throw new IllegalArgumentException("不支持的文件类型: " + contentType + "，请上传JPEG、PNG或GIF格式的图片");
        }

        // 验证文件大小
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new IllegalArgumentException("文件大小不能超过2MB");
        }

        // 创建用户目录（如果不存在）
        Path userDir = Paths.get(uploadDir, "avatars", userId);
        if (!Files.exists(userDir)) {
            Files.createDirectories(userDir);
        }

        // 生成唯一的文件名
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID() + "." + fileExtension;

        // 处理并保存图片
        Path filePath = userDir.resolve(uniqueFileName);
        processAndSaveImage(file, filePath);

        return uniqueFileName;
    }

    /**
     * 处理并保存图片（调整大小、转换格式等）
     */
    @Override
    public void processAndSaveImage(MultipartFile file, Path filePath) throws  IOException{
        try {
            // 读取原始图片
            BufferedImage originalImage = ImageIO.read(file.getInputStream());
            if (originalImage == null) {
                throw new IOException("无法读取图片文件，可能不是有效的图片格式");
            }

            // 计算新的尺寸（保持宽高比）
            int targetWidth = 200;
            int targetHeight = 200;

            // 创建新图片
            BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType());

            // 缩放图片
            Graphics2D g = resizedImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
            g.dispose();

            // 获取文件格式
            String formatName = getFormatName(file.getOriginalFilename());

            // 保存图片
            ImageIO.write(resizedImage, formatName, filePath.toFile());
        } catch (IOException e) {
            throw new IOException("图片处理失败: " + e.getMessage(), e);
        }
    }

    /**
     * 检查内容类型是否允许
     */
    @Override
    public boolean isAllowedContentType(String contentType) {
        for (String allowed : ALLOWED_CONTENT_TYPES) {
            if (allowed.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取文件扩展名
     */
    @Override
    public String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "jpg"; // 默认扩展名
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取图片格式名
     */
    @Override
    public String getFormatName(String fileName) {
        String extension = getFileExtension(fileName).toLowerCase();
        switch (extension) {
            case "png":
                return "PNG";
            case "gif":
                return "GIF";
            default:
                return "JPEG"; // 包括jpg和jpeg
        }
    }


    /**
     * 获取头像的URL路径
     */
    @Override
    public String getAvatarUrl(String userId, String fileName) {
        return "/avatars/" + userId + "/" + fileName;
    }
}
