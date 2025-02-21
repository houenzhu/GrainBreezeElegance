package com.zhe.grain.service.Impl;

import com.zhe.grain.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private static final String SRC_PATH = "\\public\\assets\\";
    private static final String PATH = "D:\\2024本科毕业设计\\GraduationDesign\\Front-Project\\grain_breeze_elegance_vue" + SRC_PATH;

    /**
     * 文件上传
     * @param multipartFile
     * @return 实际的文件路径变量
     */
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        StringBuilder sb = new StringBuilder();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        // 获取原本的文件名
        String uuid = UUID.randomUUID().toString().replace("-", "_") +
                "_" + System.currentTimeMillis();
        String originalFilename = uuid + "_" + multipartFile.getOriginalFilename();
        String dataFormat = format.replace("-", "\\");
        String fullPath = String.valueOf(sb.append(PATH).append(dataFormat));
        sb.append("\\").append(originalFilename);
        StringBuilder sb2 = new StringBuilder(SRC_PATH);
        sb2.append(dataFormat).append("\\").append(originalFilename);
        try {
            File file1 = new File(fullPath);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(sb.toString());
            byte[] bytes = multipartFile.getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            FileChannel channel = fos.getChannel();
            channel.write(buffer);
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("文件上传读取异常");
        }
        return sb2.toString().split("public")[1];
    }
}
