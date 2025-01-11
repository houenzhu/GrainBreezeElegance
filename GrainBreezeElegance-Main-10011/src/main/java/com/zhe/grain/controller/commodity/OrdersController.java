package com.zhe.grain.controller.commodity;

import com.zhe.grain.constant.ControllerConstant;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.service.commodity.OrdersService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Result;
import com.zhe.grain.vo.commodity.SkusInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping(ControllerConstant.API_PREFIX + "orders")
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

    @PostMapping("/saveOrder")
    @PreAuthorize("@zhe.hasAuthority('user:order:save')")
    public Result<Orders> saveOrder(@RequestBody List<SkusInfoVO> skusInfoVOList) {
        Orders orders = ordersService.saveOrders(skusInfoVOList);
        return Result.success("订单创建成功，请在10分钟内完成支付!", orders);
    }

    @PreAuthorize("@zhe.hasAnyAuthority('user:order:cancel', 'sys:order:cancel')")
    @PostMapping("/cancelOrders")
    public Result<Boolean> cancelOrders(@RequestBody List<String> orderIds) {
        // TODO: 2024/9/24 订单取消
        ordersService.deleteOrders(orderIds);
        return Result.success("取消成功!", true);
    }

}
