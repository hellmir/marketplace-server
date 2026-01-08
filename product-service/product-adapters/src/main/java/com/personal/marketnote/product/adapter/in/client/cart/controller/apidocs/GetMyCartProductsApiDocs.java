package com.personal.marketnote.product.adapter.in.client.cart.controller.apidocs;

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
        summary = "장바구니 상품 목록 조회",
        description = """
                작성일자: 2026-01-04
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 회원의 장바구니 상품 목록을 조회합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-04T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "회원 장바구니 상품 목록 조회 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | cartProducts | array | 장바구니 상품 목록 | [ ... ] |
                
                ---
                
                ### Response > content > cartProducts
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | product | object | 상품 | { ... } |
                | pricePolicy | object | 가격 정책 | { ... } |
                | quantity | number | 상품 수량 | 1 |
                
                ---
                
                ### Response > content > cartProducts > product
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 상품 ID | 1 |
                | name | string | 상품명 | "건기식테스트1" |
                | brandName | string | 브랜드명 | "노트왕" |
                | imageUrl | string | 상품 이미지 URL | "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png" |
                | status | string | 상태 | "ACTIVE" |
                
                ---
                
                ### Response > content > cartProducts > pricePolicy
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 가격 정책 ID | 1 |
                | price | number | 기본 판매 가격(원) | 40000 |
                | discountPrice | number | 할인 판매 가격(원) | 32000 |
                | discountRate | number | 할인율(%, 최대 소수점 1자리) | 20 |
                | accumulatedPoint | number | 구매 시 적립 포인트 | 800 |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 장바구니 상품 목록 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-04T16:50:53.326054",
                                          "content": {
                                            "cartProducts": [
                                              {
                                                "product": {
                                                  "id": 30,
                                                  "sellerId": 1,
                                                  "name": "건기식테스트1",
                                                  "brandName": "노트왕",
                                                  "imageUrl": "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png",
                                                  "status": "ACTIVE"
                                                },
                                                "pricePolicy": {
                                                  "id": 19,
                                                  "price": 60000,
                                                  "discountPrice": 40000,
                                                  "accumulatedPoint": 3000,
                                                  "discountRate": 33.3
                                                },
                                                "quantity": 5
                                              },
                                              {
                                                "product": {
                                                  "id": 30,
                                                  "sellerId": 1,
                                                  "name": "건기식테스트1",
                                                  "brandName": "노트왕",
                                                  "imageUrl": "https://marketnote.s3.amazonaws.com/product/30/1763533916081_image_600.png",
                                                  "status": "ACTIVE"
                                                },
                                                "pricePolicy": {
                                                  "id": 20,
                                                  "price": 45000,
                                                  "discountPrice": 37000,
                                                  "accumulatedPoint": 1200,
                                                  "discountRate": 17.8
                                                },
                                                "quantity": 3
                                              },
                                              {
                                                "product": {
                                                  "id": 30,
                                                  "sellerId": 1,
                                                  "name": "건기식테스트1",
                                                  "brandName": "노트왕",
                                                  "imageUrl": "https://marketnote.s3.amazonaws.com/product/30/1763533914954_image_600.png",
                                                  "status": "ACTIVE"
                                                },
                                                "pricePolicy": {
                                                  "id": 21,
                                                  "price": 40000,
                                                  "discountPrice": 32000,
                                                  "accumulatedPoint": 800,
                                                  "discountRate": 20
                                                },
                                                "quantity": 10
                                              }
                                            ]
                                          },
                                          "message": "회원 장바구니 상품 목록 조회 성공"
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
                                          "timestamp": "2025-12-30T12:12:30.013",
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
                                          "timestamp": "2025-12-30T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface GetMyCartProductsApiDocs {
}
