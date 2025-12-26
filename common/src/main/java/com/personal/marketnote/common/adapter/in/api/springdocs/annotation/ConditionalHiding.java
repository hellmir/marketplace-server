package com.personal.marketnote.common.adapter.in.api.springdocs.annotation;

import com.personal.marketnote.common.adapter.in.api.springdocs.operationservice.RedefinedOperationService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Spring Profile 중에 <code>hideapi</code>가 활성화되어 있을 경우, 이 어노테이션이 붙어 있는
 * 엔드포인트는 Swagger UI에 나타나지 않는다.
 *
 * @see RedefinedOperationService
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ConditionalHiding {
}
