package com.zhe.grain.service;

import com.zhe.grain.exception.AIException;
import com.zhe.grain.vo.TongYiResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public interface TongYiService {

    /**
     * 询问通义千问
     * @param message
     */
    TongYiResultVO askTongYi(String message) throws AIException;
}
