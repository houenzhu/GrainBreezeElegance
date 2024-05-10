package com.zhe.grain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

public class WebUtil {

    /**
     * 构建返回对象, 以流的形式
     * @param
     * @return
     */
    public static void render(HttpServletResponse response, Result<Object> result)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
        writer.close();
    }

}
