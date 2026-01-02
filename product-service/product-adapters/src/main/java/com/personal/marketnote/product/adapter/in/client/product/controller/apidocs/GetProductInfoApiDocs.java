package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema;
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
@Operation(summary = "상품 상세 정보 조회", description = """
        작성일자: 2026-01-02
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        상품의 상세 정보를 조회합니다.
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | id | number | 상품 ID | Y | 1 |
        | options | array<string> | 선택된 옵션 목록 | N | ["3박스", "60개입"] |
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
        | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
        | timestamp | string(datetime) | 응답 일시 | "2026-01-02T10:37:32.320824" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 상세 정보 조회 성공" |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | productInfo | object | 상품 상세 정보 | { ... } |
        | categories | array | 옵션 카테고리 목록 | [ ... ] |
        | selectedOptionIds | array<number> | 선택된 옵션 ID 목록 | [4, 7] |
        
        ---
        
        ### Response > content > productInfo
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 ID | 19 |
        | sellerId | number | 판매자 ID | 1 |
        | name | string | 상품명 | "스프링노트1" |
        | brandName | string | 브랜드명 | "노트왕" |
        | detail | string | 상품 상세 설명 | "스프링노트1 설명" |
        | price | number | 기본 판매 가격(원) | 164000 |
        | discountPrice | number | 할인 판매 가격(원) | 156000 |
        | discountRate | number | 할인율(%, 최대 소수점 1자리) | 17.8 |
        | accumulatedPoint | number | 구매 시 적립 포인트 | 13200 |
        | sales | number | 판매량 | 0 |
        | viewCount | number | 조회수 | 0 |
        | popularity | number | 인기도 | 0 |
        | findAllOptionsYn | boolean | 모든 옵션 선택 여부 | true |
        | productTags | array | 상품 태그 목록 | [ ... ] |
        | orderNum | number | 정렬 순서 | 19 |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        ### Response > content > productInfo > productTags
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 태그 ID | 9 |
        | productId | number | 상품 ID | 19 |
        | name | string | 상품 태그명 | "루테인" |
        | orderNum | number | 정렬 순서 | null |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        ### Response > content > categories
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 옵션 카테고리 ID | 2 |
        | name | string | 옵션 카테고리명 | "수량" |
        | orderNum | number | 정렬 순서 | 2 |
        | status | string | 상태 | "ACTIVE" |
        | options | array | 하위 옵션 목록 | [ ... ] |
        
        ---
        
        ### Response > content > categories > options
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 옵션 ID | 3 |
        | content | string | 옵션 내용 | "1박스" |
        | price | number | 옵션 가격(원) | 37000 |
        | accumulatedPoint | number | 적립 포인트 | 1200 |
        | status | string | 상태 | "ACTIVE" |
        | isSelected | boolean | 선택 여부 | false |
        """, security = {
        @SecurityRequirement(name = "bearer")
},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                schema = @Schema(implementation = StringResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-02T18:09:30.313213",
                                          "content": {
                                            "productInfo": {
                                              "id": 30,
                                              "sellerId": 1,
                                              "name": "건기식테스트1",
                                              "brandName": "노트왕",
                                              "detail": "건기식테스트건기식테스트건기식테스트",
                                              "price": 45000,
                                              "discountPrice": 37000,
                                              "discountRate": 33.3,
                                              "accumulatedPoint": 1200,
                                              "sales": 0,
                                              "viewCount": 0,
                                              "popularity": 0,
                                              "findAllOptionsYn": true,
                                              "productTags": [
                                                {
                                                  "id": 13,
                                                  "productId": 30,
                                                  "name": "루테인",
                                                  "orderNum": null,
                                                  "status": "ACTIVE"
                                                },
                                                {
                                                  "id": 14,
                                                  "productId": 30,
                                                  "name": "아스타잔틴",
                                                  "orderNum": null,
                                                  "status": "ACTIVE"
                                                }
                                              ],
                                              "orderNum": 30,
                                              "status": "ACTIVE"
                                            },
                                            "categories": [
                                              {
                                                "id": 4,
                                                "name": "수량",
                                                "orderNum": 4,
                                                "status": "ACTIVE",
                                                "options": [
                                                  {
                                                    "id": 8,
                                                    "content": "1박스",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": true
                                                  },
                                                  {
                                                    "id": 9,
                                                    "content": "3박스",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  }
                                                ]
                                              },
                                              {
                                                "id": 5,
                                                "name": "개당 수량",
                                                "orderNum": 5,
                                                "status": "ACTIVE",
                                                "options": [
                                                  {
                                                    "id": 10,
                                                    "content": "30개입",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  },
                                                  {
                                                    "id": 11,
                                                    "content": "60개입",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": true
                                                  }
                                                ]
                                              }
                                            ]
                                          },
                                          "message": "상품 상세 정보 조회 성공"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "토큰 인증 실패",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 401,
                                          "code": "UNAUTHORIZED",
                                          "timestamp": "2025-12-31T12:00:00.000",
                                          "content": null,
                                          "message": "Invalid token"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "403",
                        description = "토큰 인가 실패",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 403,
                                          "code": "FORBIDDEN",
                                          "timestamp": "2025-12-31T12:00:00.000",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "리소스 조회 실패",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 404,
                                          "code": "NOT_FOUND",
                                          "timestamp": "2026-01-02T13:32:26.542724",
                                          "content": null,
                                          "message": "상품을 찾을 수 없습니다. 전송된 상품 ID: 1"
                                        }
                                        """)
                        )
                )
        })
public @interface GetProductInfoApiDocs {
}
