package com.yoogurt.kettle.enums;

import lombok.Getter;
/**
 * Description:
 *
 * @Author Eric Lau
 * @Date 2017/8/28.
 */
@Getter
public enum StatusCode {

    /** REST远程调用失败 */
    REST_FAIL(504, "远程调用失败"),

    /** 系统出现异常 */
    SYS_ERROR(500, "系统繁忙，请稍后重试"),

    /** 表单验证未通过 */
    FORM_INVALID(400, "表单验证未通过"),

    /** 登录过期 */
    LOGIN_EXPIRE(401, "登录过期，请重新登录"),

    /** 无权限访问 */
    NO_AUTHORITY(403, "暂无权限访问"),

    /** 必填的参数没有传入 */
    PARAM_BLANK(300, "缺少必要参数"),

    /** 用户未登录，访问了需要登录的api */
    NOT_LOGIN(301, "请先登录"),

    /** 正常情况 */
    INFO_SUCCESS(200, "操作成功"),

    /** 多设备同时登录一个账号 */
    KICK_OUT(110, "该账号在另外一台设备上登录，请重新登录"),

    /** 业务处理未满足条件，导致失败 */
    BIZ_FAILED(100, "操作失败");

    private int    status;

    private String detail;

    StatusCode(int status, String detail) {
        this.status = status;
        this.detail = detail;
    }

    public static StatusCode getStatusCode(Integer status) {

        if (status == null) {
            return null;
        }

        for (StatusCode statusCode : StatusCode.values()) {
            if (status == statusCode.getStatus()) {
                return statusCode;
            }
        }
        return null;
    }
}
