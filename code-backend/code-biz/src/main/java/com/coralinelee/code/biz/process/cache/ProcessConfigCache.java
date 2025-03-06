package com.coralinelee.code.biz.process.cache;


import com.coralinelee.code.biz.process.ProcessConfig;
import com.coralinelee.code.biz.process.ProcessContext;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
public interface ProcessConfigCache {
    ProcessConfig getProcessConfig(ProcessContext context);
}
