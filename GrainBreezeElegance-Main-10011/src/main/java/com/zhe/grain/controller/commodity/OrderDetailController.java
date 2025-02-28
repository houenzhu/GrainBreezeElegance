package com.zhe.grain.controller.commodity;

import com.zhe.grain.annotation.CheckArgs;
import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.service.commodity.OrderDetailService;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.OrderDetailVO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-09-05
 */
@RestController
@AllArgsConstructor
@RequestMapping(ControllerConstant.API_PREFIX + "orderDetail")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    /**
     * 待测试
     * @param orderId
     * @return
     */
    @GetMapping("/findById")
    @PreAuthorize("@zhe.hasAnyAuthority('sys:order:detail:id', 'user:order:detail:id')")
    @CheckArgs
    public Result<OrderDetailVO> findById(@RequestParam("orderId") String orderId) {
        OrderDetailVO orderDetailVO = orderDetailService.getOrderDetailByOrderId(orderId);
        return Result.success(HttpStatus.OK.getReasonPhrase(), orderDetailVO);
    }

}
