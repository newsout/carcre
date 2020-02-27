package com.sout.carcre.service.bean;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 23:36
 */
@Data
public class TradeSell {

    /*商品销售量*/
    private int tradeNum;

    /*商品库存量*/
    private int tradeSto;
}
