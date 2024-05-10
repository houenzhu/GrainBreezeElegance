package com.zhe.grain.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.mapper.GrainBrandMapper;
import com.zhe.grain.service.GrainBrandService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
public class GrainBrandServiceImpl extends ServiceImpl<GrainBrandMapper, GrainBrandEntity>
        implements GrainBrandService {

    /**
     * 分页
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GrainBrandEntity> page = this.page(
                new Query<GrainBrandEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageUtils(page);
    }
}
