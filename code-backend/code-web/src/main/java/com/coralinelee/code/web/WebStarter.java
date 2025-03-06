package com.coralinelee.code.web;

import com.coralinelee.code.biz.BizSpringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

/**
 * Author: CoralineLee Date: 3/6/2025
 */
@Import({ BizSpringConfig.class })
@SpringBootApplication
public class WebStarter extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebStarter.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebStarter.class, args);
    }
}
