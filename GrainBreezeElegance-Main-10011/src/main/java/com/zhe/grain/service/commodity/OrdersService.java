package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 订单信息表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 分页展示接口, 页码，条目，关键词
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
}
