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

    public static <T> Result<T> makeRsp(int code, String msg) {
        Result<T> result =new Result<>();
        result.setResult(false);
        result.setCode(code).setMsg(msg);
        return result;
    }

}