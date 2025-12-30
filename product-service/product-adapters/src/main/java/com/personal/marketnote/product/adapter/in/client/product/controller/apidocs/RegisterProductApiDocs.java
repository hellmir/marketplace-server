package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.product.adapter.in.client.product.request.CreateProductRequest;
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
@Operation(summary = "상품 등록", description = """
        작성일자: 2025-12-30
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 상품을 등록합니다.
        
        - 판매자 ID, 상품명은 필수입니다.
        
        ---
        
        ## Request Body
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | sellerId | number | 판매자 회원 ID | Y | 1 |
        | name | string | 상품명 | Y | "건강기능식강1" |
        | detail | string | 상품 설명 | N | "건강기능식품1 설명" |
        | orderNumber | number | 정렬 순서 | N | 100 |
        
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 201 |
        | code | string | 응답 코드 | "SUC01" |
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
                        schema = @Schema(implementation = CreateProductRequest.class),
                        examples = @ExampleObject("""
                                {
                                    "sellerId": 1,
                                    "name": "건강기능식품1",
                                    "detail": "건강기능식품1 설명",
                                    "orderNumber": 100
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
                )
        })
public @interface RegisterProductApiDocs {
}
