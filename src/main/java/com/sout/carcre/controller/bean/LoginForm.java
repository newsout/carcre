package com.sout.carcre.controller.bean;

import com.sout.carcre.controller.bean.beanson.SignInfo;
import com.sout.carcre.mapper.bean.UserInfo;
import lombok.Data;

@Data
public class LoginForm {

    private UserInfo userInfo;

    private SignInfo signInfo;
}
