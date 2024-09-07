package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.constant.RedisConstant;
import com.zhe.grain.domain.commodity.CommoditySkuInfo;
import com.zhe.grain.domain.commodity.OrderDetail;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.mapper.commodity.OrderDetailMapper;
import com.zhe.grain.mapper.commodity.OrdersMapper;
import com.zhe.grain.service.commodity.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.commodity.SkusInfoVO;
import lombok.AllArgsConstructor;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-09-05
 */
@Service
@AllArgsConstructor
public class OrderDetailServiceImpl
        extends ServiceImpl<OrderDetailMapper, OrderDetail>
        implements OrderDetailService {

    private final OrdersMapper ordersMapper;
    private final RedisCache redisCache;


    /**
     * 创建订单和订单明细
     * 1. 创建订单表
     * 2. 创建订单明细表
     * 针对刚刚创建订单还未有支付成功的动作
     */
    @Override
    public void insertOrderDetail(List<SkusInfoVO> skuInfos, Orders orders) {
        Long userId = SecurityUtil.returnUserId();
        try {
            // 生成订单详情
            List<OrderDetail> orderDetails = skuInfos.stream()
                    .map(skuInfo -> {
                        OrderDetail orderDetail = new OrderDetail();
                        orderDetail.setCreateBy(userId.intValue())
                                .setOrderId(orders.getOrderId())
                                .setQuantity(skuInfo.getQuantity())
                                .setSkuId(skuInfo.getSkuId())
                                .setUnitPrice(skuInfo.getPrice());
                        return orderDetail;
                    }).toList();
            super.saveBatch(orderDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
