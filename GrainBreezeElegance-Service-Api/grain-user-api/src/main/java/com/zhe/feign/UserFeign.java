package com.zhe.feign;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.dto.UserDTO;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Component
@FeignClient("grain-breeze-elegance-main-10011")
public interface UserFeign {

    String API_PREFIX = ControllerConstant.API_PREFIX + "manage/user";
    String FIND_BY_ID = API_PREFIX + "/findById/{id}";


    @GetMapping(FIND_BY_ID)
    Result<UserDTO> findById(@PathVariable("id") Long id);
}
