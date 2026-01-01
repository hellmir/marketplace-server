package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.product.adapter.in.client.product.request.UpsertProductOptionsRequest;
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
        summary = "(판매자/관리자) 상품 옵션 카테고리 및 하위 옵션 목록 수정",
        description = """
                작성일자: 2026-01-01
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 상품 옵션 카테고리 및 하위 옵션 목록을 수정합니다.
                
                - 기존 옵션 카테고리 및 하위 옵션 목록 삭제 후, 생성된 튜플의 ID를 반환합니다.
                
                - 상품 판매자 본인 또는 관리자만 가능합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | productId (path) | number | 상품 ID | Y | 1 |
                | optionCategoryId (path) | number | 수정할 옵션 카테고리 ID | Y | 10 |
                | categoryName | string | 새 옵션 카테고리명 | Y | "수량" |
                | options | array<object> | 새 옵션 목록 | Y | [ ... ] |
                
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
                | message | string | 처리 결과 | "상품 옵션 카테고리 수정 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | optionCategoryId | number | 새로 생성된 옵션 카테고리 ID | 20 |
                | optionIds | array<number> | 새로 생성된 옵션 ID 목록 | [200,201] |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "productId",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "상품 ID",
                        schema = @Schema(type = "number", example = "1")
                ),
                @Parameter(
                        name = "id",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "수정할 옵션 카테고리 ID",
                        schema = @Schema(type = "number", example = "10")
                )
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = UpsertProductOptionsRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "categoryName": "수량",
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
                        description = "수정 성공",
                        content = @Content(
                                schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-01T17:32:49.097185",
                                          "content": {
                                            "optionCategoryId": 20,
                                            "optionIds": [200,201]
                                          },
                                          "message": "상품 옵션 수정 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface UpdateProductOptionsApiDocs {
}
