package com.coralinelee.code.biz.process;

import org.springframework.stereotype.Service;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Service
public class DefaultRouter implements Router {
    public DefaultRouter() {
    }

    public void route(ProcessContext context) {
        if (!"FINISH".equals(context.getStage())) {
            if ("ERROR_PROCESS".equals(context.getStage())) {
                context.setStage("FINISH");
            } else if ("POST_PROCESS".equals(context.getStage())) {
                context.setStage("FINISH");
            } else if ("PROCESS".equals(context.getStage())) {
                context.setStage("POST_PROCESS");
            } else if ("PRE_PROCESS".equals(context.getStage())) {
                context.setStage("PROCESS");
            } else {
                context.setStage("FINISH");
            }

        }
    }

    public void routeError(ProcessContext context) {
        if (!"FINISH".equals(context.getStage())) {
            if ("ERROR_PROCESS".equals(context.getStage())) {
                context.setStage("FINISH");
            } else {
                context.setStage("ERROR_PROCESS");
            }
        }
    }
}