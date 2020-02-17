package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * 碎片的收集情况（单个碎片）
 */
@Data
public class ChipCase {
    /*碎片ID*/
    private String shipId;

    /*碎片收集个数*/
    private int shipNum;
}
