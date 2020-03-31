package com.sout.carcre.service.bean;

import com.sout.carcre.controller.bean.beanson.ChipInfo;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/31 11:00
 */
public class QueryChipService {

    /*是否有卡片合成*/
    private boolean cardIsSyn;

    /*碎片信息*/
    private ChipInfo chipInfo;

    /*碎片对应的卡片ID*/
    private String cardId;
    public QueryChipService(){}
    public QueryChipService(boolean cardIsSyn, ChipInfo chipInfo, String cardId) {
        this.cardIsSyn = cardIsSyn;
        this.chipInfo = chipInfo;
        this.cardId = cardId;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
