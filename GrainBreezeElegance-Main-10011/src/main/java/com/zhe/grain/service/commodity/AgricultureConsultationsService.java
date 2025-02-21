package com.zhe.grain.service.commodity;

import com.zhe.grain.domain.commodity.AgricultureConsultations;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhe.grain.record.AgricultureConsultationsRecord;
import com.zhe.grain.service.common.BaseService;

/**
 * <p>
 * 农业咨询表 服务类
 * </p>
 *
 * @author houen_zhu
 * @since 2025-01-11
 */
public interface AgricultureConsultationsService
        extends IService<AgricultureConsultations>, BaseService {

    void pushConsultation(AgricultureConsultationsRecord record);
}
