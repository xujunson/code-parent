package com.tom.exception;

import com.tom.result.CodeMsg;

/**
 * @author: Tom
 * @date: 2020-02-28 9:47
 * @description: 全局异常
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
