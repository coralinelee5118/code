package com.coralinelee.code.remote.fact;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Service
public class FactFactory {

    @Resource
    private List<FactCheckRemoteService> services;

    private final Map<String, FactCheckRemoteService> serviceMap = new HashMap<>();

    @PostConstruct
    public void init() {
        for (FactCheckRemoteService service : services) {
            serviceMap.put(service.getType(), service);
        }
    }

    public List<FactCheckRemoteService> selectServiceList(List<String> types) {
        List<FactCheckRemoteService> factCheckRemoteServices = new ArrayList<>(types.size());

        for (String type : types) {
            FactCheckRemoteService e = serviceMap.get(type);
            if (e == null) {
                continue;
            }

            factCheckRemoteServices.add(e);
        }

        return factCheckRemoteServices;
    }
}
