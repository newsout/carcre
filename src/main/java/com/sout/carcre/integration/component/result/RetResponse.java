package com.sout.carcre.integration.component.result;

/**
 * Result返回封装类
 */
public class RetResponse {

    private final static String SUCCESS = "success";

    /**
     * 正确返回结果
     * @param <T>
     * @return
     */
    public static <T> Result<T> makeOKRsp() {
        Result<T> result =new Result<>();
        result.setResult(true);
        result.setCode(RetCode.SUCCESS).setMsg(SUCCESS);
        return result;
    }

    public static <T> Result<T> makeOKRsp(T data) {
        Result<T> result =new Result<>();
        result.setResult(true);
        result.setCode(RetCode.SUCCESS).setMsg(SUCCESS).setData(data);
        return result;
    }

    /**
     * 自定义返回code值 消息信息
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> makeRspCode(int code, T data,String msg) {
        Result<T> result =new Result<>();
        result.setResult(true);
        result.setCode(code).setMsg(msg).setData(data);
        return result;
    }

    /**
     * 错误返回结果
     * @param message
     * @param <T>
     * @return
     */
    public static <T> Result<T> makeErrRsp(String message) {
        Result<T> result =new Result<>();
        result.setResult(false);
        result.setCode(RetCode.FAIL).setMsg(message);
        return result;
    }



}