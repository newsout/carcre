package com.sout.carcre.service.bean;

import lombok.Data;

/**
 * 碎片完整信息
 */
@Data
public class ChipFullInfo {

    /*卡片ID*/
    private String cardId;

    /*碎片ID*/
    private String chipId;

    /*碎片图片路径*/
    private String chipPath;
}
