package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.mapper.commodity.OrdersMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.commodity.OrderDetailService;
import com.zhe.grain.service.commodity.OrdersService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.commodity.OrderVO;
import com.zhe.grain.vo.commodity.SkusInfoVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
@Service
@AllArgsConstructor
@Slf4j
public class OrdersServiceImpl
        extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {
    private final UserMapper userMapper;
    private final RedisCache redisCache;
    private final OrderDetailService orderDetailService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return null;
        }
        // 根据订单ID和客户名称查询订单
        String orderId = (String) params.get("orderId");
        String customerName = (String) params.get("customerName");
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(orderId)) {
            lambdaQueryWrapper.eq(Orders::getOrderId, orderId);
        }
        if (StringUtils.isNoneBlank(customerName)) {
            List<Long> ids = userMapper.findIdByNickname(customerName);
            if (!CollectionUtils.isEmpty(ids)) {
                lambdaQueryWrapper.and(wrapper -> wrapper.in(Orders::getCustomerId, ids));
            }
        }
        IPage<Orders> page = this.page(
                new Query<Orders>().getPage(params),
                lambdaQueryWrapper
        );
        List<Orders> records = page.getRecords();
        List<OrderVO> orderVo = records.stream()
                .map(item -> {
                    OrderVO orderVO = new OrderVO();
                    BeanUtils.copyProperties(item, orderVO);
                    String nickname = userMapper.selectById(item.getCustomerId()).getNickname();
                    orderVO.setCustomerName(nickname);
                    return orderVO;
                }).toList();
        return new PageUtils(orderVo, (int) page.getTotal(), (int) page.getSize(), (int) page.getCurrent());
    }

    /**
     * 生成订单, 未支付状态
     * @param skuInfos
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders saveOrders(List<SkusInfoVO> skuInfos) {
        Long userId = SecurityUtil.returnUserId();
        BigDecimal amount = new BigDecimal(0);
        Orders orders = new Orders();
        for (SkusInfoVO skuInfo : skuInfos) {
            double total = skuInfo.getQuantity().doubleValue() * skuInfo.getPrice().doubleValue();
            amount = amount.add(new BigDecimal(total));
        }
        orders.setCustomerId(userId.intValue())
                .setTotalAmount(amount)
                .setOrderStatus((byte) 0)
                .setBillingAddress("模拟数据")
                .setPaymentStatus((byte) 0)
                .setShippingMethod((byte) 0)
                .setTrackingNumber("")
                .setTrackingNumber("")
                .setShippingAddress("");
        try {
            baseMapper.insert(orders);
            // 未支付的设置10分钟有效期。过时则消除订单
            redisCache.setCacheObject(RedisConstant.NO_PAY_ORDER_KEY + ":" +
                    orders.getOrderId(), orders, 10, TimeUnit.MINUTES);
            // 插入订单明细
            orderDetailService.insertOrderDetail(skuInfos, orders);
        } catch (Exception e) {
            log.error("下订单异常: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return orders;
    }

    /**
     * 取消订单
     * zhu
     * 2024/09/24
     */
    @Override
    public void deleteOrders(List<String> orderIds) {
        List<Orders> ordersList = orderIds.stream()
                .map(orderId -> {
                    Orders orders = new Orders();
                    orders.setOrderId(orderId)
                            .setOrderStatus((byte) 5); // 取消状态
                    return orders;
                }).toList();
        super.updateBatchById(ordersList);
    }
}
