package com.personal.marketnote.file.adapter.in.client.file.controller.apidocs;

import com.personal.marketnote.file.adapter.in.client.file.request.AddFilesRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "파일 추가",
        description = """
                작성일자: 2026-01-03
                
                작성자: 성효빈
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | ownerType | string | 소유 도메인 타입 | Y | "PRODUCT" |
                | ownerId | number | 소유 도메인 ID | Y | 1 |
                | file | file[] | 업로드할 파일 목록 | Y | [file1, file2] |
                | sort | string[] | 파일 종류 목록 | Y | ["PRODUCT_CATALOG_IMAGE", "PRODUCT_REPRESENTATIVE_IMAGE"] |
                | extension | string[] | 파일 확장자 목록 | N | ["jpg", "png"] |
                | name | string[] | 파일명 목록 | N | ["스프링노트1", "스프링노트2"] |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 201: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
                | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-03T12:12:30.013" |
                | content | object | 응답 본문 | null |
                | message | string | 처리 결과 | "파일 추가 성공" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                        mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                        schema = @Schema(implementation = AddFilesRequest.class)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "파일 추가 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-03T12:12:30.013",
                                          "content": null,
                                          "message": "파일 추가 성공"
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
                                          "timestamp": "2026-01-03T12:12:30.013",
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
                                          "timestamp": "2026-01-03T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface AddFilesApiDocs {
}


