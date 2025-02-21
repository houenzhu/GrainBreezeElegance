package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.Tags;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-14
 */
public interface TagsService extends IService<Tags> {
    List<Tags> list(String type);
}
