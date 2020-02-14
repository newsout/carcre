package com.sout.carcre.integration.config.exception;


import com.sout.carcre.integration.component.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 处理异常信息
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object error(Exception e) {
        Map<String, String> map = new HashMap<>();
        Result result = new Result();
        if (e instanceof MyException) {
            MyException myException = (MyException) e;
            result.setErrorCode(myException.getErrorCode());
            result.setMsg(myException.getMessage());
        } else {
            result.setErrorCode("-1");
            result.setMsg(e.getMessage());
        }
        return result;
    }
}

