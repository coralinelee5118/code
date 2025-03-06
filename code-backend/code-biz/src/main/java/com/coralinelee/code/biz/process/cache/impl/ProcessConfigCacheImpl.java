package com.coralinelee.code.biz.process.cache.impl;

import com.coralinelee.code.biz.process.ProcessConfig;
import com.coralinelee.code.biz.process.ProcessConfigImpl;
import com.coralinelee.code.biz.process.ProcessContext;
import com.coralinelee.code.biz.process.cache.ProcessConfigCache;
import com.coralinelee.code.core.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ProcessConfigCacheImpl implements ProcessConfigCache {

    @Value("process/*.json")
    private Resource[] resources;

    private Map<String, ProcessConfig> processConfigMap;

    @PostConstruct
    private void init() {
        if (resources == null || resources.length == 0) {
            log.warn("ProcessConfigCache init failed! Templates is empty!");
            return;
        }

        Map<String, ProcessConfig> tempMap = new HashMap<>();

        try {
            for (Resource resource : resources) {
                String fileName = resource.getFilename();
                String key = fileName.substring(0, fileName.indexOf(".json"));

                String content = IOUtils.toString(resource.getInputStream());

                ProcessConfig config = JsonUtils.fromJson(content, ProcessConfigImpl.class);

                tempMap.put(key, config);
            }

            processConfigMap = tempMap;

            log.info("ProcessConfigCache init success! configMap="
                    + ToStringBuilder.reflectionToString(processConfigMap));
        } catch (Exception e) {
            log.error("ProcessConfigCache init failed!", e);
        }
    }

    @Override
    public ProcessConfig getProcessConfig(ProcessContext processContext) {
        String key = null;
        ProcessConfig config = null;
        try {
            key = processContext.getProductCode() + "_" + processContext.getBusinessCode();

            config = processConfigMap.get(key);

            return config;
        } finally {
            log.info("Select processConfig, key={}, config={}", key, config);
        }
    }
}
