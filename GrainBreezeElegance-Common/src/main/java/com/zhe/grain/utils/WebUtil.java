package com.zhe.grain.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

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

    public static void changeStringToLong(Map<String, Object> map) {
        Set<Map.Entry<String, Object>> entries =
                map.entrySet();
        entries.stream()
                .forEach(entry -> {
                    String key = entry.getKey();
                    String value = (String) entry.getValue();
                    map.put(key, Long.parseLong(value));
                });
    }

    public static String returnTimeFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(new Date());
    }

}
