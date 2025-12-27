package com.personal.marketnote.user.adapter.in.client.user.controller.apidocs;

import com.personal.marketnote.user.adapter.in.client.user.request.SignUpRequest;
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
        summary = "회원 가입",
        description = """
                작성일자: 2025-12-26
                
                작성자: 성효빈
                
                ## Description
                
                - 사용자 닉네임, 이메일 주소, 비밀번호, 성명, 전화번호를 전송해 회원으로 가입합니다.
                
                - 소셜 로그인은 OAuth2 콜백 URI를 통해 발급된 Access Token이 필요합니다.
                
                - 일반 로그인은 이메일 주소와 비밀번호가 필요합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | nickname | string | 닉네임(2~20자, 한글) | Y | "고길동" |
                | email | string | 이메일 주소(형식: example@example.com) | N | "example@example.com" |
                | password | string | 비밀번호(8자 이상, 대문자, 소문자, 숫자, 특수문자 포함) | N | "Password123!" |
                | fullName | string | 성명(2~10자, 한글) | N | "홍길동" |
                | phoneNumber | string | 전화번호(형식: 010-1234-5678) | N | "010-1234-5678" |
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | timestamp | string(datetime) | 응답 일시 | "2025-12-26T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "회원 가입 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | accessToken | string | 신규 발급된 Access Token | "f8310f8asohvh80scvh0zio3hr31d" |
                | refreshToken | string | 신규 발급된 Refresh Token | "f8310f8asohvh80scvh0zio3hr31d" |
                
                """,
        security = {@SecurityRequirement(name = "bearer")},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = SignUpRequest.class),
                        examples = @ExampleObject("""
                                {
                                    "nickname": "고길동",
                                    "email": "example@example.com",
                                    "password": "Password123!",
                                    "fullName": "홍길동",
                                    "phoneNumber": "010-1234-5678"
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "회원 가입 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "timestamp": "2025-12-26T22:52:31.889943",
                                          "content": {
                                            "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlblR5cGUiOiJBQ0NFU1NfVE9LRU4iLCJpYXQiOjE3NjE1MjgzMTYsImV4cCI6MTc2MTUzMDExNiwic3ViIjoiOCIsInJvbGVJZHMiOlsiUk9MRV9CVVlFUiJdLCJ1c2VySWQiOjgsImF1dGhWZW5kb3IiOiJOQVRJVkUifQ.3nhlFNz9NBfcJKIteTICcUyN7F1w068CJKu5uy5kB0I",
                                            "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlblR5cGUiOiJSRUZSRVNIX1RPS0VOIiwiaWF0IjoxNzYxNDg2NzUxLCJleHAiOjE3NjI2OTYzNTEsInN1YiI6Im51bGwiLCJyb2xlSWRzIjpbIlJPTEVfQlVZRVIiXSwiYXV0aFZlbmRvciI6Ik5BVElWRSJ9._YvI9YT4aklPzJdN5D4IRqx0uzsyz4wjBMgCLGcf_CA"
                                          },
                                          "message": "회원 가입 성공"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "OAuth2 토큰이 존재하지 않음",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 400,
                                          "timestamp": "2025-12-26T09:53:02.089234",
                                          "content": null,
                                          "message": "OAuth2 토큰이 존재하지 않습니다. 로그인 후 다시 시도해 주세요."
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
                                          "timestamp": "2025-12-26T09:53:02.089234",
                                          "content": null,
                                          "message": "이미 가입된 회원입니다."
                                        }
                                        """)
                        )
                )
        }
)
public @interface SignUpApiDocs {
}
