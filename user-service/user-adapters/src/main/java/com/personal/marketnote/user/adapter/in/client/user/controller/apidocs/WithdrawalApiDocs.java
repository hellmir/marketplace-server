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
        summary = "회원 탈퇴",
        description = """
                작성일자: 2025-12-29
                
                 작성자: 성효빈
                
                 ## Description
                
                 - 회원에서 탈퇴합니다.
                
                 - 연결된 모든 소셜 로그인(애플 로그인 제외) 정보가 삭제됩니다.
                
                 ## Request
                
                 | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                 | --- | --- | --- | --- | --- |
                 | googleAccessToken | string | 구글 액세스 토큰(현재 구글 로그인 상태인 경우 함께 전송) | N | "f8310f8asohvh80scvh0zio3hr31d" |
                
                 ---
                
                 ## Response
                
                 | **키** | **타입** | **설명** | **예시** |
                 | --- | --- | --- | --- |
                 | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                 | code | string | 응답 코드 | "SUC01" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "회원 탈퇴 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2025-12-26T22:52:31.889943",
                                          "content": null,
                                          "message": "회원 탈퇴 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface WithdrawalApiDocs {
}
