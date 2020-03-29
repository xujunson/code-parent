package com.atu.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tom
 * @date: 2020-03-29 15:57
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordTable {

    private Long unitId;
    private String keyword;
}
