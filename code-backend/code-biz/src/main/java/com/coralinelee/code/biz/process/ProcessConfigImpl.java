package com.coralinelee.code.biz.process;

import lombok.Data;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Data
public class ProcessConfigImpl implements ProcessConfig {
    private Map<String, ProcessStage> processStageMap;

    public ProcessConfigImpl() {
    }

    public List<String> getActions(String stage) {
        ProcessStage processStage = this.processStageMap.get(stage);
        return processStage == null ? new ArrayList() : processStage.getActions();
    }

    public String getRouter(String stage) {
        ProcessStage processStage = this.processStageMap.get(stage);
        return processStage == null ? "" : processStage.getRouter();
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
