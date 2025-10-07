package com.work.service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public interface ImageUploadService {
    String saveAvatar(MultipartFile file, String type, String userId) throws IOException;

    void processAndSaveImage(MultipartFile file, Path filePath) throws IOException;

    boolean isAllowedContentType(String contentType);

    String getFileExtension(String fileName);

    String getFormatName(String fileName);

    String getUrl(Integer id, String type, String fileName);

}
