package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.ChipInfo;
import lombok.Data;

/**
 * 请求获取随机碎片
 */

public class QueryChip {
    /*是否有卡片合成*/
    private boolean cardIsSyn;

    /*碎片信息*/
    private ChipInfo chipInfo;
    public QueryChip(){}
    public QueryChip(boolean cardIsSyn, ChipInfo chipInfo) {
        this.cardIsSyn = cardIsSyn;
        this.chipInfo = chipInfo;
    }

    public boolean isCardIsSyn() {
        return cardIsSyn;
    }

    public void setCardIsSyn(boolean cardIsSyn) {
        this.cardIsSyn = cardIsSyn;
    }

    public ChipInfo getChipInfo() {
        return chipInfo;
    }

    public void setChipInfo(ChipInfo chipInfo) {
        this.chipInfo = chipInfo;
    }

    @Override
    public String toString() {
        return "QueryChip{" +
                "cardIsSyn=" + cardIsSyn +
                ", chipInfo=" + chipInfo +
                '}';
    }
}
