package com.zhe.grain.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface FileService {
    String uploadFile(MultipartFile multipartFile);
}
