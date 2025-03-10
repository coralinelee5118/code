package com.coralinelee.code.biz.process;


import lombok.Getter;
import lombok.Setter;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Getter
@Setter
public class ProcessContext {
    private String        productCode;
    private String        businessCode;
    private String        stage     = "PRE_PROCESS";
    private Object        entity;
    private String        errorCode = "SUCCESS";
    private String        errorMsg  = "执行成功";
    private ProcessConfig processConfig;

    public <T> T getEntity() {
        return (T) this.entity;
    }
}