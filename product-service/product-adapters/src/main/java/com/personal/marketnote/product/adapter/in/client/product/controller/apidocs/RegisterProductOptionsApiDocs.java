package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductOptionsRequest;
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
        summary = "(판매자/관리자) 상품 옵션 카테고리 및 하위 옵션 목록 생성",
        description = """
                작성일자: 2026-01-01
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 요청 상품에 새로운 옵션 카테고리와 하위 옵션들을 생성합니다.
                
                - 각 카테고리는 최소 1개 이상의 옵션을 포함해야 합니다.
                
                - 상품 판매자 본인 또는 관리자만 가능합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | productId (path) | number | 상품 ID | Y | 1 |
                | categoryName | string | 생성할 옵션 카테고리명 | Y | "용량" |
                | options | array<object> | 카테고리 내 옵션 목록 | Y | [ ... ] |
                
                ### Request > options
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | content | string | 옵션 내용 | Y | "1박스" |
                | price | number | 옵션 가격(원) | N | 37000 |
                | accumulatedPoint | number | 적립 포인트 | N | 1200 |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 200 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-01T10:00:00.000" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "상품 옵션 등록 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | productId | number | 상품 ID | 1001 |
                | id | number | 옵션 카테고리 ID | 10 |
                | optionIds | array<number> | 옵션 ID 목록 | [100,101] |
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
                        schema = @Schema(implementation = RegisterProductOptionsRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "categoryName": "용량",
                                  "options": [
                                    { "content": "1박스", "price": 37000, "accumulatedPoint": 1200 },
                                    { "content": "3박스", "price": 99000, "accumulatedPoint": 1200 }
                                  ]
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK",
                        content = @Content(
                                schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-01T17:32:49.097185",
                                          "content": {
                                            "id": 2,
                                            "optionIds": [
                                              2,
                                              3
                                            ]
                                          },
                                          "message": "상품 옵션 등록 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface RegisterProductOptionsApiDocs {
}


