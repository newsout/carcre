package com.sout.carcre.integration.component.result;

import java.io.Serializable;

/**
 * controller返回json格式
 * @param <T>
 */

public class Result<T> implements Serializable {

    private static final long   serialVersionUID = -5186399321109828905L;

    public int code;

    private boolean result;

    private String msg;

    private T data;

    public Result<T> setCode(RetCode retCode) {
        this.code = retCode.code;
        return this;
    }

    /**错误情况**/
    public Result<T> setErrorCode(String code) {
        this.code = Integer.parseInt(code);
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
