package com.coralinelee.code.biz;

import com.coralinelee.code.remote.RemoteSpringConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RemoteSpringConfig.class)
public class BizSpringConfig {
}
