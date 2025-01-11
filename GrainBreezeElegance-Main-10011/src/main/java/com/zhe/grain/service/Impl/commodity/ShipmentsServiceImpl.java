package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhe.grain.domain.commodity.Shipments;
import com.zhe.grain.mapper.commodity.ShipmentsMapper;
import com.zhe.grain.service.commodity.ShipmentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 物流信息表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2024-08-12
 */
@Service
@AllArgsConstructor
@Slf4j
public class ShipmentsServiceImpl extends ServiceImpl<ShipmentsMapper, Shipments> implements ShipmentsService {

    private final ShipmentsMapper shipmentsMapper;

    /**
     * 分页 + 模糊查询
     * @param params
     * @return
     */
    @Override
    public PageUtils page(Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        LambdaQueryWrapper<Shipments> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(orderId)) {
            queryWrapper.eq(Shipments::getOrderId, orderId);
        }
        IPage<Shipments> page = this.page(new Query<Shipments>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }
}
