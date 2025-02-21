package com.zhe.grain.service.Impl.commodity;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhe.grain.domain.commodity.PlantInfo;
import com.zhe.grain.dto.PlantInfoDTO;
import com.zhe.grain.mapper.commodity.PlantInfoMapper;
import com.zhe.grain.service.commodity.PlantInfoService;
import com.zhe.grain.utils.PageUtils;
import com.zhe.grain.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-02-19
 */
@Service
@Slf4j
public class PlantInfoServiceImpl extends ServiceImpl<PlantInfoMapper, PlantInfo>
        implements PlantInfoService {

    /**
     * 分页查询
     * @param params 查询参数
     * @return
     */
    @Override
    public PageUtils page(Map<String, Object> params) {
        LambdaQueryWrapper<PlantInfo> wrapper = new LambdaQueryWrapper<>();
        IPage<PlantInfo> page;
        try {
            String plantName = (String) params.get("plantName");
            if (StringUtils.isNotBlank(plantName)) {
                wrapper.like(PlantInfo::getPlantName, plantName);
            }
            wrapper.orderBy(true, false, PlantInfo::getCreatedAt);
            page = this.page(
                    new Query<PlantInfo>().getPage(params),
                    wrapper
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new PageUtils(page);
    }

    @Override
    public void savePlantInfo(PlantInfoDTO plantInfoDTO) throws Exception {
        PlantInfo plantInfo = new PlantInfo();
        List<Integer> suitableTemperature = plantInfoDTO.getSuitableTemperature();
        Integer min = suitableTemperature.get(0);
        Integer max = suitableTemperature.get(1);
        BeanUtils.copyProperties(plantInfoDTO, plantInfo);
        plantInfo.setSuitableTemperature(min + "-" + max + "℃");
        plantInfo.setMin(min).setMax(max);
        saveOrUpdate(plantInfo);
    }

    @Override
    public PlantInfoDTO findById(Long id) {
        PlantInfo plantInfo = this.baseMapper.selectById(id);
        PlantInfoDTO plantInfoDTO = new PlantInfoDTO();
        BeanUtils.copyProperties(plantInfo, plantInfoDTO);
        List<Integer> list = Arrays.asList(plantInfo.getMin(), plantInfo.getMax());
        plantInfoDTO.setSuitableTemperature(list);
        return plantInfoDTO;
    }

    /**
     * 逻辑删除
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        this.baseMapper.deleteById(id);
    }
}
