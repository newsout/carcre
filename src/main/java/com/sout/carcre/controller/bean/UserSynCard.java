package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.ChipCase;
import lombok.Data;

import java.util.List;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/3/29 22:51
 */
@Data
public class UserSynCard {
    /*合成卡片的个数*/
    private int synCardNum;

    /*碎片剩余情况*/
    private List<ChipCase> chipCaseList;
}
