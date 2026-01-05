package com.personal.marketnote.order.adapter.in.client.order.controller.apidocs;

import com.personal.marketnote.order.adapter.in.client.order.request.ChangeOrderStatusRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "주문 상태 변경",
        description = """
                작성일자: 2026-01-05
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 주문 상태를 변경합니다.
                
                - 가격 정책 목록 전송 시, 해당하는 주문 상품의 상태만 변경합니다.
                
                - 가격 정책 목록 미전송 시, 모든 주문 상품의 상태를 변경합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | pricePolicyIds | array<number> | 가격 정책 ID 목록 | Y | [1, 2, 3] |
                | orderStatus | string | 주문 상태 | Y | "PAYMENT_PENDING" |
                
                ---
                
                ### Request > pricePolicyIds
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | number | number | 가격 정책 ID | Y | 1 |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-05T12:12:30.013" |
                | content | object | 응답 본문 | null |
                | message | string | 처리 결과 | "주문 상태 변경 성공" |
                """, security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "id",
                        description = "주문 ID",
                        in = ParameterIn.PATH,
                        required = true,
                        schema = @Schema(type = "number")
                )
        },
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = ChangeOrderStatusRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "pricePolicyIds": [1, 2, 3],
                                  "orderStatus": "PAYMENT_PENDING"
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "주문 상태 변경 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-05T12:12:30.013",
                                          "content": null,
                                          "message": "주문 상태 변경 성공"
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
                                          "timestamp": "2026-01-05T12:12:30.013",
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
                                          "timestamp": "2026-01-05T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface ChangeOrderStatusApiDocs {
}

