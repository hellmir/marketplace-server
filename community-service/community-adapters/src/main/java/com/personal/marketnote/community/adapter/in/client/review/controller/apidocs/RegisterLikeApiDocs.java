package com.personal.marketnote.community.adapter.in.client.review.controller.apidocs;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterLikeRequest;
import io.swagger.v3.oas.annotations.Operation;
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
        summary = "좋아요 등록",
        description = """
                작성일자: 2026-01-10
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 대상에 좋아요를 등록합니다.
                
                - 대상: 리뷰/게시글
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | targetType | string | 대상 유형(REVIEW/BOARD) | Y | "REVIEW" |
                | targetId | number | 대상 ID | Y | 10 |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | HTTP 상태 코드 | 201 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-10T11:00:00.000" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "좋아요 등록 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | targetType | string | 대상 유형 | "REVIEW" |
                | targetId | number | 대상 ID | 10 |
                | userId | number | 회원 ID | 5 |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = RegisterLikeRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "targetType": "REVIEW",
                                  "targetId": 10
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "좋아요 등록 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-10T11:00:00.000",
                                          "content": {
                                            "targetType": "REVIEW",
                                            "targetId": 10,
                                            "userId": 5
                                          },
                                          "message": "좋아요 등록 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface RegisterLikeApiDocs {
}
