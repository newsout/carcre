package com.sout.carcre.integration.config.exception;

/**
 * 自定义异常情况类
 */
public class MyException extends Exception {
    private static final long serialVersionUID = 3302912015345408891L;

    /**错误code*/
    private String            errorCode;

    /**
     * 打印日志类型为warning,有些字段基本校验无需打印error级别的日志
     */
    private boolean           warnFlag;

    /**
     * 是否打印堆栈信息
     */
    private boolean           printStackFlag   = true;

    /**
     * Instantiates a new  exception.
     */
    public MyException() {
    }

    /**
     * Instantiates a new  exception.
     *
     * @param message the message
     */
    public MyException(String message) {
        super(message);
    }

    /**
     * Instantiates a new  exception.
     *
     * @param message   the message
     * @param errorCode the error code
     */
    public MyException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Instantiates a new exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new exception.
     *
     * @param cause the cause
     */
    public MyException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new  exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Is warn flag boolean.
     *
     * @return the boolean
     */
    public boolean isWarnFlag() {
        return warnFlag;
    }

    /**
     * Sets warn flag.
     *
     * @param warnFlag the warn flag
     * @return the warn flag
     */
    public MyException setWarnFlag(boolean warnFlag) {
        this.warnFlag = warnFlag;
        return this;
    }

    /**
     * Is print stack flag boolean.
     *
     * @return the boolean
     */
    public boolean isPrintStackFlag() {
        return printStackFlag;
    }

    /**
     * Sets print stack flag.
     *
     * @param printStackFlag the print stack flag
     * @return the print stack flag
     */
    public MyException setPrintStackFlag(boolean printStackFlag) {
        this.printStackFlag = printStackFlag;
        return this;
    }
}
