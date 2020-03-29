package com.atu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tom
 * @date: 2020-03-29 15:58
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictTable {

    private Long unitId;
    private String province;
    private String city;
}

