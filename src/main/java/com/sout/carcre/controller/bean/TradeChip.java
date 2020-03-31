package com.sout.carcre.controller.bean;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/20 22:30
 */
@Data
public class TradeChip {
    /*卡片名称*/
    private String cardName;

    /*碎片图像路径*/
    private String chipPath;

    /*用户剩余碳积分个数*/
    private int userGrade;
}
