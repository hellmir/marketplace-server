package com.personal.marketnote.product.adapter.in.client.category.controller.apidocs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(summary = "카테고리 목록 조회", description = """
        작성일자: 2025-12-31
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 전송된 parentId의 하위 카테고리 목록을 반환합니다.
        
        - parentId를 전송하지 않는 경우 최상위 카테고리 목록을 반환합니다.
        
        - 활성(ACTIVE) 상태만 반환됩니다.
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | parentId | number | 상위 카테고리 ID (null이면 루트) | N | 1 |
        
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** |
        | --- | --- | --- |
        | statusCode | number | 상태 코드 |
        | code | string | 응답 코드 |
        | timestamp | string(datetime) | 응답 일시 |
        | content | object | 응답 본문 |
        | message | string | 처리 결과 |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | categories | array | 카테고리 목록 | [ ... ] |
        
        ---
        
        ### Response > content > categories
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 카테고리 ID | 10001 |
        | parentCategoryId | number | 상위 카테고리 ID | 1 |
        | name | string | 카테고리명 | "루테인" |
        | status | string | 상태 | "ACTIVE" |
        """, responses = {
        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema.class), examples = {
                @ExampleObject(name = "성공", value = """
                        {
                          "statusCode": 200,
                          "code": "SUC01",
                          "timestamp": "2025-12-31T11:10:54.208185",
                          "content": {
                            "categories": [
                              {
                                "id": 10001,
                                "parentCategoryId": 1,
                                "name": "베스트",
                                "status": "ACTIVE"
                              },
                              {
                                "id": 10002,
                                "parentCategoryId": 1,
                                "name": "비타민B",
                                "status": "ACTIVE"
                              },
                              {
                                "id": 10003,
                                "parentCategoryId": 1,
                                "name": "오메가",
                                "status": "ACTIVE"
                              },
                              {
                                "id": 10004,
                                "parentCategoryId": 1,
                                "name": "루테인",
                                "status": "ACTIVE"
                              },
                              {
                                "id": 10005,
                                "parentCategoryId": 1,
                                "name": "아스타잔틴",
                                "status": "ACTIVE"
                              }
                            ]
                          },
                          "message": "카테고리 목록 조회 성공"
                        }
                        """)
        }))
})
public @interface GetCategoriesApiDocs {
}
