package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.SysUser;
import com.zhe.grain.domain.commodity.CommoditySkuInfo;
import com.zhe.grain.domain.commodity.OrderDetail;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.mapper.commodity.CommoditySkuInfoMapper;
import com.zhe.grain.mapper.commodity.OrderDetailMapper;
import com.zhe.grain.mapper.commodity.OrdersMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.commodity.OrderDetailService;
import com.zhe.grain.utils.RedisCache;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.commodity.OrderDetailVO;
import com.zhe.grain.vo.commodity.SkusInfoVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private final CommoditySkuInfoMapper skuInfoMapper;
    private final UserMapper userMapper;


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
                                .setUnitPrice(skuInfo.getPrice())
                                .setIsDeleted((byte) 0)
                                .setUpdateBy(userId.intValue());
                        return orderDetail;
                    }).toList();
            super.saveBatch(orderDetails);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查看订单详情
     * houen_zhu
     * 2024-09-07
     */
    @Override
    public OrderDetailVO getOrderDetailByOrderId(String orderId) {
        List<OrderDetail> orderDetails = baseMapper.selectList(
                new LambdaQueryWrapper<OrderDetail>()
                        .eq(OrderDetail::getOrderId, orderId)
        );
        Orders orders = ordersMapper.selectOne(
                new LambdaQueryWrapper<Orders>()
                        .eq(Orders::getOrderId, orderId)
        );
        SysUser sysUser = userMapper.selectById(orders.getCustomerId());
        OrderDetailVO orderDetailVO = new OrderDetailVO();
        BeanUtils.copyProperties(orders, orderDetailVO);
        orderDetailVO.setCreatedTime(orders.getCreatedAt())
                        .setUpdatedTime(orders.getUpdatedAt());
        orderDetailVO.setCustomerName(sysUser.getNickname());
        List<Long> ids = orderDetails.stream()
                .map(OrderDetail::getSkuId).toList();
        List<CommoditySkuInfo> commoditySkuInfos = skuInfoMapper.selectBatchIds(ids);
        List<OrderDetailVO.ProductInfo> productInfos = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetails) {
            for (CommoditySkuInfo commoditySkuInfo : commoditySkuInfos) {
                if (commoditySkuInfo.getSkuId().equals(orderDetail.getSkuId())) {
                    OrderDetailVO.ProductInfo productInfo = new OrderDetailVO.ProductInfo();
                    productInfo.setProductId(commoditySkuInfo.getSkuId().toString())
                            .setProductName(commoditySkuInfo.getSkuName())
                            .setQuantity(orderDetail.getQuantity())
                            .setUnitPrice(commoditySkuInfo.getPrice())
                            .setTotalPrice(commoditySkuInfo.getPrice()
                                    .multiply(new BigDecimal(orderDetail.getQuantity())));
                    productInfos.add(productInfo);
                }
            }
        }
        orderDetailVO.setProductList(productInfos);
        return orderDetailVO;
    }
}
