package com.coralinelee.code.biz.process.impl;

import com.coralinelee.code.biz.process.*;
import com.coralinelee.code.biz.process.exception.ProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Slf4j
@Service
public class ProcessExecutorImpl implements ProcessExecutor {
    private static final Router DEFAULT_ROUTER = new DefaultRouter();
    @Autowired
    private ApplicationContext applicationContext;

    public void execute(ProcessContext context) {
        if (log.isDebugEnabled()) {
            log.debug("[ProcessExecutor] start to process, context=" + context);
        }

        if (!"FINISH".equals(context.getStage())) {
            Router router = this.getRouter(context);
            String currentAction = null;
            String currentStage = context.getStage();

            try {
                ProcessConfig processConfig = context.getProcessConfig();
                List<String> actions = processConfig.getActions(context.getStage());

                for (String action : actions) {
                    currentAction = action;

                    BusinessAction a = applicationContext.getBean(currentAction, BusinessAction.class);
                    log.info("[ProcessExecutor] processing, action={}", action);
                    a.process(context);
                }

                router.route(context);
            } catch (ProcessException pe) {
                context.setErrorCode(pe.getErrorCode());
                context.setErrorMsg(pe.getErrorMsg());
                log.warn("[ProcessExecutor] process internal error! stage=" + context.getStage() + ", errorAction=" + currentAction + ", context=" + context + ", errorCode=" + pe.getErrorCode() + ", errorMsg=" + pe.getErrorMsg());
                router.routeError(context);
            } catch (Exception var15) {
                context.setErrorCode("SYS_ERROR");
                context.setErrorMsg(var15.getMessage());
                log.error("[ProcessExecutor] process error! stage={}, errorAction={}, context={}", context.getStage(), currentAction, context, var15);
                router.routeError(context);
            } finally {
                log.info(String.format("[ProcessExecutor] process turn from [%s] to [%s], router=%s", currentStage, context.getStage(), router.getClass().getSimpleName()));
            }

            this.execute(context);
        }
    }

    private Router getRouter(ProcessContext context) {
        ProcessConfig config = context.getProcessConfig();
        String routerBean = config.getRouter(context.getStage());
        if (routerBean != null && !routerBean.equals("")) {
            try {
                return (Router) this.applicationContext.getBean(routerBean, Router.class);
            } catch (Exception var5) {
                return DEFAULT_ROUTER;
            }
        } else {
            return DEFAULT_ROUTER;
        }
    }
}
