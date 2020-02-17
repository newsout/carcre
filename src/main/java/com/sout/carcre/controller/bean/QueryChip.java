package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.ChipInfo;
import lombok.Data;

/**
 * 请求获取随机碎片
 */
@Data
public class QueryChip {
    /*是否有卡片合成*/
    private boolean cardIsSyn;

    /*碎片信息*/
    private ChipInfo chipInfo;
}
