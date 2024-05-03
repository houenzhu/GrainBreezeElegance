package com.zhe.grain.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhe.grain.annotation.AdminLoginAnnotation;
import com.zhe.grain.entity.AdminEntity;
import com.zhe.grain.service.AdminService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.utils.ResultMsgEnum;
import com.zhe.grain.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 1.0
 * @Author 朱厚恩
 * 对管理员控制层进行拦截
 */

@Component
public class AdminAccessInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;


    // 调用controller方法前先执行该前置处理器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取该方法上的注解
            AdminLoginAnnotation adminLoginAnnotation = handlerMethod
                    .getMethodAnnotation(AdminLoginAnnotation.class);
            if (adminLoginAnnotation == null) {
                // 没有该注解，则直接放行
                return true;
            }
            // 判断是否携带user-ticket
            String header = request.getHeader("User-Ticket");
            if (!StringUtils.hasText(header)) {
                return false;
            }
            AdminEntity admin = this.getAdmin(header);
            if (admin == null) {
                // 未登录
                this.render(response, ResultMsgEnum.INVALID_COOKIE);
                return false;
            }
            // 如果存在, 将admin对象存入threadLocal
            ThreadLocalUtil.setThreadLocalVal(admin);
        }
        return true;
    }

    // 构建返回对象, 以流的形式
    private void render(HttpServletResponse response, ResultMsgEnum resultMsgEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        Result<ResultMsgEnum> error = Result.error(resultMsgEnum);
        writer.write(new ObjectMapper().writeValueAsString(error));
        writer.flush();
        writer.close();
    }

    /**
     * 通过ticket获取管理员实体类
     * @param header
     * @return
     */
    private AdminEntity getAdmin(String header) {
        return adminService.getAdminByHeader(header);
    }
}
