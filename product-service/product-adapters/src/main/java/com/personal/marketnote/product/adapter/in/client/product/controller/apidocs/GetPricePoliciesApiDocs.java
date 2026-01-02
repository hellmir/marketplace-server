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
@Operation(summary = "상품 가격 정책 목록 조회", description = """
        작성일자: 2026-01-02
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        상품의 가격 정책 목록(기본/조합)을 조회합니다.
        
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 200 |
        | code | string | 응답 코드 | "SUC01" |
        | timestamp | string(datetime) | 응답 일시 | "2026-01-02T10:37:32.320824" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 가격 정책 목록 조회 성공" |
        
        ### content
        | **키** | **타입** | **설명** |
        | --- | --- | --- |
        | policies | array | 가격정책 목록 |
        
        ### content.policies[*]
        | **키** | **타입** | **설명** |
        | --- | --- | --- |
        | id | number | 정책 ID |
        | price | number | 정가 |
        | discountPrice | number | 할인 판매가 |
        | accumulatedPoint | number | 적립 포인트 |
        | discountRate | number | 할인율 |
        | basePolicy | boolean | 기본 정책 여부(조합 미연결) |
        | optionIds | array<number> | 연결된 옵션 ID 목록(조합 정책일 때) |
        """,
        security = {@SecurityRequirement(name = "bearer")},
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
                                          "timestamp": "2026-01-02T10:37:32.320824",
                                          "content": {
                                            "policies": [
                                              { "id": 12, "price": 45000, "discountPrice": 37000, "accumulatedPoint": 1200, "discountRate": 17.8, "basePolicy": true, "optionIds": [] },
                                              { "id": 13, "price": 55000, "discountPrice": 43000, "accumulatedPoint": 1500, "discountRate": 21.8, "basePolicy": false, "optionIds": [3,7] }
                                            ]
                                          },
                                          "message": "상품 가격 정책 목록 조회 성공"
                                        }
                                        """)
                        )
                )
        })
public @interface GetPricePoliciesApiDocs {
}


