package com.personal.shop.user.adapter.in.client.user.controller.apidocs;

import com.personal.shop.user.adapter.in.client.user.request.SignUpRequest;
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
@Operation(
        summary = "[회원 가입/로그인 팝업] 회원 가입",
        description = """
                작성일자: 2025-12-26
                
                작성자: 성효빈
                
                ## Description
                
                - 사용자 닉네임을 전송해 회원으로 가입합니다.
                
                - OAuth2 콜백 URI를 통해 발급된 Access Token이 필요합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | nickname | string | 닉네임(2~10자, 한글) | Y | "성효빈" |
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201: 생성됨 / 409: 중복 회원 |
                | timestamp | string(datetime) | 응답 일시 | "2025-12-26T12:12:30.013" |
                | content | object | 토큰 페이로드 | { "accessToken": "...", "refreshToken": "..." } |
                | message | string | 처리 결과 | "회원 가입 성공" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = SignUpRequest.class),
                        examples = @ExampleObject("""
                                {
                                    "nickname": "성효빈"
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "회원 가입 성공. content에는 발급된 accessToken, refreshToken이 담긴다.",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "timestamp": "2025-12-26T12:12:30.013",
                                          "message": null,
                                          "content": {
                                            "accessToken": "<jwt-access-token>",
                                            "refreshToken": "<jwt-refresh-token>"
                                          }
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "중복된 회원 등록",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 409,
                                          "timestamp": "2025-12-26T20:46:00.451254",
                                          "errorCode": "USR003",
                                          "errorName": "DUPLICATE_USER",
                                          "message": "이미 가입된 회원입니다.",
                                          "detail": { "id": "1513348125" }
                                        }
                                        """)
                        )
                )
        }
)
public @interface SignUpApiDocs {
}
