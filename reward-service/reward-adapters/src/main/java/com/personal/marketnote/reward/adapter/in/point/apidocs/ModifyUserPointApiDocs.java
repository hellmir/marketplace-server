package com.personal.marketnote.reward.adapter.in.point.apidocs;

import com.personal.marketnote.reward.adapter.in.point.request.ModifyUserPointRequest;
import com.personal.marketnote.reward.adapter.in.point.response.RegisterUserPointResponseSchema;
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
        summary = "(관리자) 회원 포인트 적립/차감",
        description = """
                작성일자: 2026-01-17
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 회원 포인트를 적립하거나 차감합니다.
                
                - 300 포인트를 차감하는 경우, changeType: "DEDUCTION", 금액: 300으로 전송합니다.
                
                - 포인트는 0 미만이 될 수 없습니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | userId (path) | number | 회원 ID | Y | 100 |
                | changeType | string | ACCRUAL: 적립, DEDUCTION: 차감 | Y | "ACCRUAL" |
                | amount | number | 변경 포인트(양수) | Y | 500 |
                | sourceType | string | 출처 유형 | Y | "USER" |
                | sourceId | number | 출처 ID | Y | 123 |
                | reason | string | 사유 | N | "추천인 코드 등록 적립" |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | HTTP 상태 코드 | 200 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 시간 | "2026-01-17T12:00:00.000" |
                | content | object | 수정 후 포인트 정보 | { ... } |
                | message | string | 처리 결과 | "회원 포인트 수정 성공" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "userId",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "회원 ID",
                        schema = @Schema(type = "number", example = "100")
                )
        },
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = ModifyUserPointRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "changeType": "ACCRUAL",
                                  "amount": 500,
                                  "sourceType": "OFFERWALL",
                                  "sourceId": 123,
                                  "reason": "오퍼월 적립"
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 포인트 수정 성공",
                        content = @Content(
                                schema = @Schema(implementation = RegisterUserPointResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-17T12:00:00.000",
                                          "content": {
                                            "userId": 100,
                                            "amount": 1500,
                                            "addExpectedAmount": 0,
                                            "expireExpectedAmount": 0,
                                            "createdAt": "2026-01-17T11:00:00"
                                          },
                                          "message": "회원 포인트 수정 성공"
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
                                          "timestamp": "2026-01-07T12:12:30.013",
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
                                          "timestamp": "2026-01-07T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "회원 포인트 정보 없음",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 404,
                                          "code": "NOT_FOUND",
                                          "timestamp": "2026-01-17T16:32:56.132152",
                                          "content": null,
                                          "message": "회원 포인트 정보를 찾을 수 없습니다. userId=101"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "포인트가 0 미만이 됨",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 400,
                                          "code": "BAD_REQUEST",
                                          "timestamp": "2026-01-17T16:32:29.683831",
                                          "content": null,
                                          "message": "포인트는 0 미만이 될 수 없습니다. 차감 후 포인트: -100"
                                        }
                                        """)
                        )
                )
        }
)
public @interface ModifyUserPointApiDocs {
}
