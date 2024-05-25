package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.entity.GrainBrandEntity;
import com.zhe.grain.mapper.commodity.GrainBrandMapper;
import com.zhe.grain.service.commodity.GrainBrandService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @version 1.0
 * @Author 朱厚恩
 */

@Service
public class GrainBrandServiceImpl extends ServiceImpl<GrainBrandMapper, GrainBrandEntity>
        implements GrainBrandService {

    @Autowired
    private GrainBrandMapper grainBrandMapper;

    /**
     * 分页
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<GrainBrandEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNoneBlank(key)) {
            queryWrapper.eq("id", key).or().like("name", key);
        }
        IPage<GrainBrandEntity> page = this.page(
                new Query<GrainBrandEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    /**
     * 更新品牌数据
     * @param grainBrandEntity
     */
    @Override
    public void update(GrainBrandEntity grainBrandEntity) {
        grainBrandMapper.updateById(grainBrandEntity);
    }
}
