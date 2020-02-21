package com.sout.carcre.service.bean;

import lombok.Data;

/**
 * 卡片对应的碎片信息
 */
@Data
public class ChipFromCard {

    /*卡片ID*/
    private String cardId;

    /*卡片对应的碎片信息*/
    private String cardContent;
}
