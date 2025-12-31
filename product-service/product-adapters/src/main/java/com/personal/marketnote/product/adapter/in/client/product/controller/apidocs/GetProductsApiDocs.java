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
        작성일자: 2025-12-31
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
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
        | code | string | 응답 코드 | "SUC01" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" |
        | timestamp | string(datetime) | 응답 일시 | "2025-12-31T12:00:00.000" |
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
        | name | string | 상품명 | "상품A" |
        | detail | string | 설명 | "설명" |
        | sales | number | 판매량 | 0 |
        | orderNum | number | 정렬 번호 | 1 |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        """, security = {@SecurityRequirement(name = "bearer")}, responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class), examples = @ExampleObject("""
                {
                  "statusCode": 200,
                  "code": "SUC01",
                  "timestamp": "2025-12-31T12:00:00.000",
                  "content": {
                    "products": [
                      { "id": 1, "sellerId": 10, "name": "상품A", "detail": "설명", "sales": 0, "orderNum": 1, "status": "ACTIVE" }
                    ]
                  },
                  "message": "상품 목록 조회 성공"
                }
                """)))
})
public @interface GetProductsApiDocs {
}
