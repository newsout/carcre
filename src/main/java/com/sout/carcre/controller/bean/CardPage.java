package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.ChipNum;
import com.sout.carcre.controller.bean.beanson.UserForCard;
import lombok.Data;

import java.util.List;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 20:52
 */
@Data
public class CardPage {
    /*用户数据*/
    private UserForCard userForCard;

    /*卡片列表*/
    private List<ChipNum> chipNumList;
}
