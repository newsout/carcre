package com.sout.carcre.controller.bean;

import lombok.Data;

/**
 * 返回合成卡片请求
 */
@Data
public class SynCard {
    /*合成卡片的图片路径*/
    private String cardPath;

    /*合成卡片的等级*/
    private String cardHeight;
}
