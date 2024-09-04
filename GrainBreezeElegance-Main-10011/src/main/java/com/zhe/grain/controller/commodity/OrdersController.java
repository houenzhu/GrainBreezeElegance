package com.zhe.grain.controller.commodity;

import com.zhe.grain.service.commodity.OrdersService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 订单信息表 前端控制器
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
@RestController
@RequestMapping("/grain/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping("/list")
    @PreAuthorize("@zhe.hasAuthority('sys:order:list')")
    public Result<PageUtils> list(@RequestParam Map<String, Object> params) {
        PageUtils pageUtils = ordersService.queryPage(params);
        return Result.success(pageUtils);
    }

}
