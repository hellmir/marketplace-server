package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.product.adapter.in.client.product.request.RegisterProductRequest;
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
@Operation(summary = "(판매자/관리자) 상품 등록", description = """
        작성일자: 2025-12-30
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 판매할 상품을 등록합니다.
        
        - 판매자 또는 관리자만 가능합니다.
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | sellerId | number | 판매자 회원 ID | Y | 1 |
        | name | string | 상품명 | Y | "스프링노트1" |
        | brandName | string | 브랜드명 | N | "노트왕" |
        | detail | string | 상품 설명 | N | "스프링노트1 설명" |
        | price | number | 상품 기본 판매 가격(원) | Y | 100000 |
        | discountPrice | number | 상품 할인 가격(원) | N | 90000 |
        | accumulatedPoint | number | 구매 시 적립 포인트 | Y | 1000 |
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 201: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
        | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" |
        | timestamp | string(datetime) | 응답 일시 | "2025-12-30T12:12:30.013" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 등록 성공" |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 ID | 1 |
        """, security = {@SecurityRequirement(name = "bearer")},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = RegisterProductRequest.class),
                        examples = @ExampleObject("""
                                {
                                    "sellerId": 1,
                                    "name": "스프링노트1",
                                    "brandName": "노트왕",
                                    "detail": "스프링노트1 설명",
                                    "price": 10000,
                                    "discountPrice": 9000,
                                    "accumulatedPoint": 1000
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "상품 등록 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "code": "SUC01",
                                          "timestamp": "2025-12-30T12:12:30.013",
                                          "content": {
                                            "id": 1
                                          },
                                          "message": "상품 등록 성공"
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
public @interface RegisterProductApiDocs {
}
