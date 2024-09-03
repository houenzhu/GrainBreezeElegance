package com.zhe.grain.service.aichat;

import com.zhe.grain.exception.AIException;
import com.zhe.grain.vo.tongyi.TongYiResultVO;

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
