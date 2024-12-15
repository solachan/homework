package com.xiepuxin.incident.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 失败
     */
    public static final int FAILURE = 500;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 链路跟踪ID
     */
    private String traceId;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, "操作成功");
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg, T data) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAILURE, "操作失败");
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAILURE, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAILURE, "操作失败");
    }

    public static <T> R<T> fail(String msg, T data) {
        return restResult(data, FAILURE, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> fail(int code, String msg,String traceId) {
        return restResult(null, code, msg,traceId);
    }

    public static <T> R<T> fail(T data,int code, String msg,String traceId) {
        return restResult(data, code, msg,traceId);
    }




    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        return r;
    }

    private static <T> R<T> restResult(T data, int code, String msg,String traceId) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setData(data);
        r.setMsg(msg);
        r.setTraceId(traceId);
        return r;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }

}
