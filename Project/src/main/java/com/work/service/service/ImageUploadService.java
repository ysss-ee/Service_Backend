package com.work.service.service;

import java.nio.file.Path;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageUploadService {
    String saveAvatar (MultipartFile file,String type, String userId) throws IOException;
    void processAndSaveImage(MultipartFile file, Path filePath) throws  IOException;
    boolean isAllowedContentType(String contentType);
    String getFileExtension(String fileName);
    String getFormatName(String fileName);
    String getUrl(Integer id,String type, String fileName);

}
