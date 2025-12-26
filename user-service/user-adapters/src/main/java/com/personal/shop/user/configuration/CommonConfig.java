package com.personal.shop.user.configuration;

import com.personal.shop.common.application.aop.LoggingAspect;
import com.personal.shop.common.domain.exception.GlobalExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({LoggingAspect.class, GlobalExceptionHandler.class})
public class CommonConfig {
}
