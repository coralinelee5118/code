package com.coralinelee.code.biz.process.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Getter
@Setter
public class ProcessException extends RuntimeException {
    private String errorCode;
    private String errorMsg;

    public ProcessException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public ProcessException(String errorCode, String errorMsg, Throwable t) {
        super(t);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
