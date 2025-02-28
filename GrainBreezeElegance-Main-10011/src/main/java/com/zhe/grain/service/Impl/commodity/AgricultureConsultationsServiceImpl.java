package com.zhe.grain.service.Impl.commodity;

import com.zhe.grain.domain.commodity.AgricultureConsultations;
import com.zhe.grain.mapper.commodity.AgricultureConsultationTagsMapper;
import com.zhe.grain.mapper.commodity.AgricultureConsultationsMapper;
import com.zhe.grain.mapper.commodity.TagsMapper;
import com.zhe.grain.mapper.user.UserMapper;
import com.zhe.grain.record.AgricultureConsultationsRecord;
import com.zhe.grain.service.commodity.AgricultureConsultationsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.SecurityUtil;
import com.zhe.grain.vo.commodity.AgricultureConsultationsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 农业咨询表 服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
@Service
@AllArgsConstructor
@Slf4j
public class AgricultureConsultationsServiceImpl extends ServiceImpl<AgricultureConsultationsMapper, AgricultureConsultations>
        implements AgricultureConsultationsService {

    // 定义字符串常量
    private static final String TYPE = "type";
    private final UserMapper userMapper;
    private final TagsMapper tagsMapper;
    private final AgricultureConsultationTagsMapper consultationTagsMapper;
    /**
     * 实现分页展示
     * @param params
     * @return
     */
    @Override
    public PageUtils page(Map<String, Object> params) {
        String typeStr = (String) params.get(TYPE);
        Integer pageSize = getSize(params);
        Integer page = getCurrentPage(params); // 获取前端的当前页
        int pageNum = (page - 1) * pageSize;
        List<AgricultureConsultations> agricultureConsultations;
        if (StringUtils.isBlank(typeStr)) {
            agricultureConsultations = baseMapper.selectAllByTags(null, pageNum, pageSize);
        } else {
            agricultureConsultations =
                    baseMapper.selectAllByTags(Integer.parseInt(typeStr), pageNum, pageSize);
        }
        List<AgricultureConsultationsVO> newPage = agricultureConsultations.stream().map(record -> {
            AgricultureConsultationsVO agricultureConsultationsVO = new AgricultureConsultationsVO();
            Integer authorId = record.getAuthorId();
            // TODO 这里做了循环查询，后期需要优化
            String nickname = userMapper.selectById(authorId).getNickname();
            agricultureConsultationsVO.setAuthorName(nickname);
            BeanUtils.copyProperties(record, agricultureConsultationsVO);
            return agricultureConsultationsVO;
        }).toList();
        return new PageUtils(newPage, getTotalCount(params), pageSize, page);
    }

    @Override
    public Integer getTotalCount(Map<String, Object> params) {
        String type = (String) params.get(TYPE);
        Integer totalCountByTags;
        if (StringUtils.isBlank(type)) {
            totalCountByTags = baseMapper.getTotalCountByTags(null);
        } else {
            totalCountByTags = baseMapper.getTotalCountByTags(Integer.parseInt(type));
        }
        return totalCountByTags;
    }

    /**
     * 咨询发布
     * @param record
     */
    @Override
    @Transactional
    public void pushConsultation(AgricultureConsultationsRecord record) {
        AgricultureConsultations agricultureConsultations = new AgricultureConsultations();
        try {
            String tagName = tagsMapper.selectById(record.selectTab()).getName();
            BeanUtils.copyProperties(record, agricultureConsultations);
            agricultureConsultations.setAuthorId(SecurityUtil.returnUserId().intValue())
                    .setCategory(tagName);
            baseMapper.insert(agricultureConsultations);
            baseMapper.insertRelationConTag(agricultureConsultations.getId(), record.selectTab());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
