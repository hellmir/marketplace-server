package com.personal.marketnote.commerce.adapter.in.client.order.controller.apidocs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "회원 주문 내역 조회",
        description = """
                작성일자: 2026-01-06
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                회원의 주문 내역을 조회합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | period | string | 조회 기간(ONE_MONTH, THREE_MONTHS, SIX_MONTHS, ONE_YEAR, ALL) | N | ONE_YEAR |
                | status | string | 주문 상태 필터(SHIPPING, DELIVERED, CONFIRMED, CANCEL_EXCHANGE_REFUND, ALL) | N | SHIPPING |
                | productName | string | 상품명 검색 키워드 | N | "공책" |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-06T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "회원 주문 내역 조회 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | orderHistories | array | 날짜별 주문 내역 묶음 | [ ... ] |
                
                ---
                ### Response > content > orderHistories
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | orderDate | string(date) | 주문 생성일(일 단위) | "2025-11-01" |
                | count | number | 해당 날짜의 주문 건수 | 3 |
                | orders | array | 주문 목록(주문 ID 내림차순) | [ ... ] |
                
                ---
                
                ### Response > content > orderHistories > orders
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 주문 ID | 1 |
                | sellerId | number | 판매자 회원 ID | 1 |
                | buyerId | number | 구매자 회원 ID | 1 |
                | orderStatus | string | 주문 상태 | "DELIVERED" |
                | totalAmount | number | 총 주문 금액(원) | 100000 |
                | paidAmount | number | 결제 금액(원) | 100000 |
                | couponAmount | number | 쿠폰 할인 금액(원) | 5000 |
                | pointAmount | number | 포인트 사용 금액(원) | 5000 |
                | orderProducts | array | 주문 상품 목록 | [ ... ] |
                
                ---
                
                ### Response > content > orderHistories > orders > orderProducts
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | pricePolicyId | number | 가격 정책 ID | 1 |
                | quantity | number | 주문 수량 | 2 |
                | unitAmount | number | 단위 금액(원) | 50000 |
                | imageUrl | string | 상품 이미지 URL | "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png" |
                | orderStatus | string | 주문 상태 | "PAYMENT_PENDING" |
                | productId | number | 상품 ID | 10 |
                | productName | string | 상품명 | "밀토스테놀" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 주문 내역 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                                "statusCode": 200,
                                                "code": "SUC01",
                                                "timestamp": "2026-01-06T11:18:48.109239",
                                                "content": {
                                                  "orderHistories": [
                                                    {
                                                      "orderDate": "2026-01-06",
                                                      "count": 2,
                                                      "orders": [
                                                        {
                                                          "id": 3,
                                                          "sellerId": 1,
                                                          "buyerId": 4,
                                                          "orderStatus": "DELIVERED",
                                                          "totalAmount": 120000,
                                                          "paidAmount": 120000,
                                                          "couponAmount": 5000,
                                                          "pointAmount": 5000,
                                                          "orderProducts": [
                                                            {
                                                              "pricePolicyId": 13,
                                                              "quantity": 2,
                                                              "unitAmount": 50000,
                                                              "imageUrl": "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png",
                                                              "orderStatus": "DELIVERED"
                                                            }
                                                          ]
                                                        },
                                                        {
                                                          "id": 2,
                                                          "sellerId": 1,
                                                          "buyerId": 4,
                                                          "orderStatus": "CONFIRMED",
                                                          "totalAmount": 120000,
                                                          "paidAmount": 120000,
                                                          "couponAmount": 5000,
                                                          "pointAmount": 5000,
                                                          "orderProducts": [
                                                            {
                                                              "pricePolicyId": 14,
                                                              "quantity": 10,
                                                              "unitAmount": 70000,
                                                              "imageUrl": "https://marketnote.s3.amazonaws.com/product/30/1763533916081_image_600.png",
                                                              "orderStatus": "CONFIRMED"
                                                            }
                                                          ]
                                                        }
                                                      ]
                                                    }
                                                  ]
                                                },
                                                "message": "회원 주문 내역 조회 성공"
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
                                          "timestamp": "2026-01-06T12:12:30.013",
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
                                          "timestamp": "2026-01-06T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface GetOrdersApiDocs {
}
