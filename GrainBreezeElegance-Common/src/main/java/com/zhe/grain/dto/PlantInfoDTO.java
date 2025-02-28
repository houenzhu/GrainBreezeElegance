package com.zhe.grain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @Author 朱厚恩
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PlantInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long plantId;

    private String plantName;

    private String coverImage;

    private String adoptionDetails;

    private String careDetails;

    private List<Integer> suitableTemperature;

}
