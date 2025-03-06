package com.coralinelee.code.biz.process;

import java.util.List;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
public interface ProcessConfig {
    List<String> getActions(String var1);

    String getRouter(String var1);
}
