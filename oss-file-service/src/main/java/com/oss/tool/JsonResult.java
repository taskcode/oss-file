package com.oss.tool;




import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Json 统一返回消息类
 *
 */
public class JsonResult implements Serializable {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAILURED = -1;
    public static final int CODE_PARAM = 600;
    public static final int CODE_BLANK = 601;
    public static final int CODE_TIME_FORMAT = 602;
    public static final int CODE_NOT_FOUND = 404;
    public static final String MESSAGE_PARAM = "参数异常";
    public static final String MESSAGE_BLANK = "必填参数不能为空";
    public static final String MESSAGE_TIME_FORMAT = "时间格式不正确,必须为:yyyyMMddHHmmss";
    private static final long serialVersionUID = -1491499610244557029L;
    public static String[] NOOP = new String[] {};
    private int code;
    private String message;
    private Object data;

    private JsonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    private JsonResult() {

    }

    /**
     * 处理成功，并返回数据
     *
     * @param data
     *            数据对象
     * @return data
     */
    public static final JsonResult success(Object data) {
        return new JsonResult(CODE_SUCCESS, "成功", data);
    }

    /**
     * 处理成功
     *
     * @return data
     */
    public static final JsonResult success() {
        return new JsonResult(CODE_SUCCESS, "成功", NOOP);
    }

    /**
     * 处理成功
     *
     * @param message
     *            消息
     * @return data
     */
    public static final JsonResult success(String message) {
        return new JsonResult(CODE_SUCCESS, message, NOOP);
    }

    /**
     * 处理成功
     *
     * @param message
     *            消息
     * @param data
     *            数据对象
     * @return data
     */
    public static final JsonResult success(String message, Object data) {
        return new JsonResult(CODE_SUCCESS, message, data);
    }

    /**
     * 处理失败，并返回数据（一般为错误信息）
     *
     * @param code
     *            错误代码
     * @param message
     *            消息
     * @return data
     */
    public static final JsonResult failure(int code, String message) {
        return new JsonResult(code, message, NOOP);
    }

    /**
     * 处理失败 -- 参数异常
     *
     * @return data
     */
    public static final JsonResult paramError() {
        return new JsonResult(CODE_PARAM, MESSAGE_PARAM, NOOP);
    }

    /**
     * 处理失败 -- 参数异常
     *
     * @return data
     */
    public static final JsonResult paramError(String paramName) {
        return new JsonResult(CODE_PARAM, paramName + MESSAGE_PARAM, NOOP);
    }

    /**
     * 处理失败
     * code: 404
     * message: 自定义消息
     * @return data
     */
    public static final JsonResult notFound(String message) {
        return new JsonResult(CODE_NOT_FOUND, message, NOOP);
    }

    /**
     * 处理失败
     * code: 601
     * message: 必填参数不能为空
     * @return
     */
    public static final JsonResult blank() {
        return new JsonResult(CODE_BLANK, MESSAGE_BLANK, NOOP);
    }

    /**
     * 处理失败
     * code: 602
     * message: 时间格式不正确,必须为:yyyyMMddHHmmss
     */
    public static final JsonResult errorTime() {
        return new JsonResult(CODE_TIME_FORMAT, MESSAGE_TIME_FORMAT, NOOP);
    }
    /**
     * 处理失败
     *
     * @param message
     *            消息
     * @return data
     */
    public static final JsonResult failure(String message) {
        return failure(CODE_FAILURED, message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setData(JsonObject data) {
        JsonObject jsonObject = new JsonObject();
        this.data = data;
    }

    @Override
    public String toString() {
        return "JsonResult [code=" + code + ", message=" + message + ", data="
                + data + "]";
    }


}
