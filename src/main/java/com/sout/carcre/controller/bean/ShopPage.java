package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.TradeData;
import lombok.Data;

import java.util.List;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 22:48
 */
@Data
public class ShopPage {

    /*用户现在拥有的碳积分*/
    private int userGrade;

    /*商品列表*/
    private List<TradeData> tradeDataList;
}
