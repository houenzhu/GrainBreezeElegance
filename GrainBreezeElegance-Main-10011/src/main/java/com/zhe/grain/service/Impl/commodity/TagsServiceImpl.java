package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhe.grain.domain.commodity.Tags;
import com.zhe.grain.mapper.commodity.TagsMapper;
import com.zhe.grain.service.commodity.TagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
@Service
@AllArgsConstructor
public class TagsServiceImpl extends ServiceImpl<TagsMapper, Tags> implements TagsService {

    private final TagsMapper tagsMapper;


    @Override
    public List<Tags> list(String type) {
        List<Tags> list = tagsMapper.list();
        if ("switch".equals(type)) { // 若是下拉框，将全部的过滤
            list = list.stream()
                    .filter(item -> item.getId() != 4)
                    .toList();
        }
        return list;
    }
}
