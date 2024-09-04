package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.commodity.Orders;
import com.zhe.grain.mapper.commodity.OrdersMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.service.commodity.OrdersService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import com.zhe.grain.vo.commodity.OrderVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单信息表 服务实现类
 * </p>
 * @author houen_zhu
 * @since 2024-08-12
 */
@Service
@AllArgsConstructor
public class OrdersServiceImpl
        extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    private final UserMapper userMapper;

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
}
