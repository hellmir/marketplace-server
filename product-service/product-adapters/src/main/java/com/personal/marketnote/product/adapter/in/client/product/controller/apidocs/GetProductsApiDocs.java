package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(summary = "상품 목록 조회", description = """
        작성일자: 2026-01-01
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 상품 목록을 조회합니다.
        
        - categoryId 미 전송 시 전체 상품 목록을 반환합니다.
        
        - categoryId 전송 시 해당 카테고리의 상품 목록을 반환합니다.
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | categoryId | number | 카테고리 ID | N | 1001 |
        
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
        | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" |
        | timestamp | string(datetime) | 응답 일시 | "2026-01-01T10:37:32.320824" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 목록 조회 성공" |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | products | array | 상품 목록 | [ ... ] |
        
        ---
        
        ### Response > content > products
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 ID | 1 |
        | sellerId | number | 판매자 ID | 10 |
        | name | string | 상품명 | "파우더커피1" |
        | detail | string | 설명 | "파우더커피1 설명" |
        | sales | number | 판매량 | 0 |
        | orderNum | number | 정렬 순서 | 1 |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        """, security = {@SecurityRequirement(name = "bearer")}, responses = {
        @ApiResponse(
                responseCode = "200",
                description = "OK",
                content = @Content(
                        schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class),
                        examples = @ExampleObject("""
                                {
                                  "statusCode": 200,
                                  "code": "SUC01",
                                  "timestamp": "2026-01-01T10:56:37.657799",
                                  "content": {
                                    "products": [
                                      {
                                        "id": 11,
                                        "sellerId": 4,
                                        "name": "파우더커피1",
                                        "detail": "파우더커피1 설명",
                                        "sales": 0,
                                        "orderNum": 11,
                                        "status": "ACTIVE"
                                      },
                                      {
                                        "id": 13,
                                        "sellerId": 1,
                                        "name": "파우더커피2",
                                        "detail": "파우더커피2 설명",
                                        "sales": 0,
                                        "orderNum": 13,
                                        "status": "ACTIVE"
                                      },
                                      {
                                        "id": 15,
                                        "sellerId": 1,
                                        "name": "파우더커피3",
                                        "detail": "파우더커피3 설명",
                                        "sales": 0,
                                        "orderNum": 15,
                                        "status": "ACTIVE"
                                      },
                                      {
                                        "id": 16,
                                        "sellerId": 1,
                                        "name": "파우더커피4",
                                        "detail": "파우더커피4 설명",
                                        "sales": 0,
                                        "orderNum": 16,
                                        "status": "ACTIVE"
                                      },
                                      {
                                        "id": 17,
                                        "sellerId": 1,
                                        "name": "파우더커피5",
                                        "detail": "파우더커피5 설명",
                                        "sales": 0,
                                        "orderNum": 17,
                                        "status": "ACTIVE"
                                      },
                                      {
                                        "id": 18,
                                        "sellerId": 1,
                                        "name": "파우더커피6",
                                        "detail": "파우더커피6 설명",
                                        "sales": 0,
                                        "orderNum": 18,
                                        "status": "ACTIVE"
                                      }
                                    ]
                                  },
                                  "message": "상품 목록 조회 성공"
                                }
                                """)))
})
public @interface GetProductsApiDocs {
}
