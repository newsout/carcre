package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.CardRoughInfo;
import com.sout.carcre.controller.bean.beanson.ChipCase;
import com.sout.carcre.controller.bean.beanson.UserForCard;
import lombok.Data;

import java.util.List;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 21:27
 */
@Data
public class ChipCollCase {

    /*卡片收集情况*/
    private CardRoughInfo cardRoughInfo;

    /*用户碎片收集情况*/
    private List<ChipCase> chipCaseList;

    /*是否有卡片合成*/
    private boolean chipIsSyn;
}
