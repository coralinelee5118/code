package com.coralinelee.code.biz.process;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Service
public @interface Action {
    @AliasFor(
            annotation = Service.class
    )
    String value() default "";
}
