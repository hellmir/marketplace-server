package com.personal.marketnote.user.adapter.in.client.user.controller.apidocs;

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
        summary = "회원 정보 조회",
        description = """
                작성일자: 2025-12-28
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                회원 정보를 조회합니다.
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | timestamp | string(datetime) | 응답 일시 | "2025-12-26T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "회원 정보 조회 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | userInfo | object | 회원 정보 | { ... } |
                
                ### Response > content > userInfo
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 회원 ID | 1 |
                | authVendor | string | 인증 제공자 | "NATIVE" / "KAKAO" / "GOOGLE" / "APPLE" |
                | oidcId | string | OIDC ID | "1234567890" |
                | nickname | string | 닉네임 | "고길동" |
                | email | string | 이메일 주소 | "example@example.com" |
                | fullName | string | 성명 | "홍길동" |
                | phoneNumber | string | 전화번호 | "010-1234-5678" |
                | referenceCode | string | 참조 코드 | "1234567890" |
                | roleId | string | 역할 ID | "ROLE_BUYER" |
                
                """,
        security = {@SecurityRequirement(name = "bearer")},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 정보 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "timestamp": "2025-12-28T10:41:37.842294",
                                          "content": {
                                            "userInfo": {
                                              "id": 61,
                                              "authVendor": "NATIVE",
                                              "oidcId": null,
                                              "nickname": "고길동",
                                              "email": "example@example.com",
                                              "fullName": "홍길동",
                                              "phoneNumber": "010-1234-5678",
                                              "referenceCode": "a12bc3",
                                              "roleId": "ROLE_BUYER",
                                              "lastLoggedInAt": "2025-12-27T14:39:25.881029"
                                            }
                                          },
                                          "message": "회원 정보 조회 성공"
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
                                          "timestamp": "2025-12-27T16:22:02.196732",
                                          "content": null,
                                          "message": "Invalid token"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "존재하지 않는 회원",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 404,
                                          "timestamp": "2025-12-26T09:53:02.089234",
                                          "content": null,
                                          "message": "존재하지 않는 회원입니다. 회원 ID: 1"
                                        }
                                        """)
                        )
                )
        }
)
public @interface GetUserInfoApiDocs {
}
