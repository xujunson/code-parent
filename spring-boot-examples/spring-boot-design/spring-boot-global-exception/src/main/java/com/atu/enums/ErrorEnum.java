package com.atu.enums;

/**
 * @author: Tom
 * @create: 2023-02-15 11:16
 * @Description: 错误枚举，拒绝硬编码
 */
public enum ErrorEnum {
    // 数据操作错误定义
    SUCCESS(200, "成功"),
    NO_PERMISSION(403,"你没得权限"),
    NO_AUTH(401,"未登录"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器异常请联系管理员"),
    ;

    /** 错误码 */
    private Integer errorCode;

    /** 错误信息 */
    private String errorMsg;

    ErrorEnum(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
