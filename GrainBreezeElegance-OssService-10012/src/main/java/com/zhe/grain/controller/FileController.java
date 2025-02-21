package com.zhe.grain.controller;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.service.FileService;
import com.zhe.grain.utils.Result;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@RestController
@RequestMapping("/file")
@AllArgsConstructor
@Slf4j
public class FileController {

    private final FileService fileService;
    @PostMapping("/upload")
    public Result<String> fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        return Result.success(fileService.uploadFile(multipartFile));
    }
}
