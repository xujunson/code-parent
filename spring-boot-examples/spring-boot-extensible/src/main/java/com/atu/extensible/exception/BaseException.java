package com.atu.extensible.exception;

import com.atu.extensible.constant.ErrorCodeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseException extends RuntimeException {

    private String code;
    private String message;

    public BaseException(ErrorCodeEnum errorCodeEnum,String message,Throwable throwable){
        super(message,throwable);
        this.code = errorCodeEnum.name();
        this.message = errorCodeEnum.getMsg();
    }

    public BaseException(ErrorCodeEnum errorCodeEnum,Exception e){
        this(errorCodeEnum,e.getMessage(),e);
    }

    public BaseException(ErrorCodeEnum errorCodeEnum){
        this(errorCodeEnum,null,null);
    }
}
