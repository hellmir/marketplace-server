package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterPricePolicyRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "(판매자/관리자) 상품 가격 정책 등록",
        description = """
                작성일자: 2026-01-01
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 새로운 상품 가격 정책을 등록합니다.
                
                - 상품 목록/정보 조회 시 마지막에 등록한 가격 정책이 적용됩니다.
                
                - 상품 판매자 본인 또는 관리자만 가능합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | productId (path) | number | 상품 ID | Y | 1 |
                | price | number | 정가 | Y | 45000 |
                | currentPrice | number | 현재 판매가 | Y | 37000 |
                | accumulatedPoint | number | 적립 포인트 | Y | 1200 |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-01T10:00:00.000" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "상품 가격 정책 등록 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 가격 정책 내역 ID | 100 |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "productId",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "상품 ID",
                        schema = @Schema(type = "number", example = "1")
                )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = RegisterPricePolicyRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "price": 45000,
                                  "currentPrice": 37000,
                                  "accumulatedPoint": 1200
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "등록 성공",
                        content = @Content(
                                schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-01T17:32:49.097185",
                                          "content": {
                                            "id": 100
                                          },
                                          "message": "상품 가격 정책 등록 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface RegisterPricePolicyApiDocs {
}


