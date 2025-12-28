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
        summary = "추천 회원 초대 코드 등록",
        description = """
                작성일자: 2025-12-28
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                자신을 추천한 회원의 초대 코드를 등록합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | referenceCode | string | 자신을 추천한 회원의 초대 코드 | Y | "1234567890" |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | timestamp | string(datetime) | 응답 일시 | "2025-12-26T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "초대 코드 등록 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "초대 코드 등록 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "timestamp": "2025-12-28T09:34:09.367178",
                                          "content": null,
                                          "message": "초대 코드 등록 성공"
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
                        description = "존재하지 않는 초대 코드",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 404,
                                          "timestamp": "2025-12-26T09:53:02.089234",
                                          "content": null,
                                          "message": "존재하지 않는 회원입니다. 전송된 추천 회원 초대 코드: %s"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "409",
                        description = "이미 추천 회원 초대 코드 등록",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 409,
                                          "timestamp": "2025-12-28T09:29:51.207978",
                                          "content": null,
                                          "message": "이미 추천 회원 초대 코드를 등록했습니다."
                                        }
                                        """)
                        )
                )
        }
)
public @interface RegisterReferredUserCodeApiDocs {
}
