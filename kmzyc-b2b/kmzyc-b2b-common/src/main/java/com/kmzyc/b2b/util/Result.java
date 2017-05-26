package com.kmzyc.b2b.util;

/**
 * @author wanwen
 */
public class Result {

    private String code;
    private String message;
    private Object retnObj;

    private Result(String code) {

        this.code = code;
    }

    public Result(String code, String desc) {
        this(code, desc, null);
    }

    public Result(String code, String message, Object retnObj) {
        this.code = code;
        this.message = message;
        this.retnObj = retnObj;

    }

    public static Result success(Object retnObj) {
        return new Result("200", "成功", retnObj);
    }

    public static Result success(String message, Object retnObj) {
        return new Result("200", message, retnObj);
    }

    public static Result error(Object retnObj) {
        return new Result("500", "失败", retnObj);
    }

    public static Result error(String message, Object retnObj) {
        return new Result("500", message, retnObj);
    }

    public static Result common(String code,String message, Object retnObj) {
        return new Result(code, message, retnObj);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetnObj() {
        return retnObj;
    }

    public void setRetnObj(Object retnObj) {
        this.retnObj = retnObj;
    }
}
