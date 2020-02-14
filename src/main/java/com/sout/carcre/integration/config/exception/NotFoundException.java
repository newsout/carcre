package com.sout.carcre.integration.config.exception;


import com.sout.carcre.integration.component.result.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 404返回代码
 */
@Controller
public class NotFoundException implements ErrorController {
    /**重写错误路径**/
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = {"/error"})
    @ResponseBody
    public Result error(HttpServletRequest request) {
        ModelAndView modelAndView=new ModelAndView();
        Map<String, Object> body = new HashMap<>();
        Result result=new Result();
        result.setErrorCode("404");
        result.setMsg("页面丢失，请确认请求路径");
        result.setResult(false);
        result.setData(null);
        return result;
    }
}

