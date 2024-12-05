package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.CommoditySkuInfo;
import com.zhe.grain.domain.commodity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.service.common.BaseService;
import com.zhe.grain.vo.commodity.OrderDetailVO;
import com.zhe.grain.vo.commodity.SkusInfoVO;

import java.util.List;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-09-05
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 下订单
     */
    void insertOrderDetail(List<SkusInfoVO> skuInfos, Orders orders);

    /**
     * 查看订单详情
     * houen_zhu
     * 2024-09-07
     */
    OrderDetailVO getOrderDetailByOrderId(String orderId);
}
