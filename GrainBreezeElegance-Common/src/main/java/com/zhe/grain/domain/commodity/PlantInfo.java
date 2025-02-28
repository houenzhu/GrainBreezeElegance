package com.zhe.grain.domain.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author houen_zhu
 * @since 2025-02-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("plant_info")
public class PlantInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "plant_id", type = IdType.AUTO)
    private Long plantId;

    private String plantName;

    private String coverImage;

    private String adoptionDetails;

    private String careDetails;

    private String suitableTemperature;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer min;

    private Integer max;

    @TableLogic
    private Boolean deleted;
}
