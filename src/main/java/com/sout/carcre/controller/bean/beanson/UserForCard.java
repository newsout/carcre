package com.sout.carcre.controller.bean.beanson;

import lombok.Data;

/**
 * @author ndd
 * @version 2.x
 * @date 2020/2/27 20:53
 */
@Data
public class UserForCard {
    /*用户昵称*/
    private String nickName;

    /*用户图像路径*/
    private String userImagePath;

    /*用户现有卡片数量*/
    private int cardAllNum;
}
