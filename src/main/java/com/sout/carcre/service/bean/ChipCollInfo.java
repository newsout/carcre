package com.sout.carcre.service.bean;

import lombok.Data;

@Data
public class ChipCollInfo implements Comparable<ChipCollInfo> {

    /*卡片ID*/
    private String cardId;

    /*碎片ID*/
    private String shipId;

    /*卡片+碎片*/
    private String cardChip;

    /*碎片数量*/
    private int chipNum;

    /*按照卡片结合碎片升序排序*/
    @Override
    public int compareTo(ChipCollInfo o) {
        return this.getCardChip().compareTo(o.getCardChip());
    }
}
