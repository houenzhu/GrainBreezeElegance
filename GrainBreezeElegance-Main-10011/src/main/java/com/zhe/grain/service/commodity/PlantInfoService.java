package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.PlantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.dto.PlantInfoDTO;
import com.zhe.grain.service.common.BaseService;
import com.zhe.grain.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-02-19
 */
public interface PlantInfoService extends IService<PlantInfo>, BaseService {
    void savePlantInfo(PlantInfoDTO plantInfoDTO) throws Exception;

    PlantInfoDTO findById(Long id);

    void deleteById(Long id);
}
