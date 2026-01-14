package com.personal.marketnote.community.adapter.in.client.post.controller.apidocs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "게시글 목록 조회",
        description = """
                작성일자: 2026-01-13
                
                작성자: 성효빈
                
                - 게시글 목록을 조회합니다.
                
                - 페이로드에 cursor 값이 없는 경우(첫 페이지): 총 상품 개수 반환 O
                
                - 페이로드에 cursor 값이 있는 경우(더 보기): 총 상품 개수 반환 X
                
                - 게시판
                
                    - **비회원**
                
                        - **"NOTICE": 공지**
                
                        - **"FAQ": FAQ**
                
                        - **"PRODUCT_INQUERY": 상품 상세 정보 페이지의 상품 문의**
                
                            - targetType: PRICE_POLICY
                
                            - targetId: pricePolicyId
                
                    - **회원(인증 필요)**
                
                        - **"PRODUCT_INQUERY": 나의 상품 문의**
                
                            - targetType: 미전송
                
                            - targetId: 미전송
                
                        - **"ONE_ON_ONE_INQUERY": 나의 1:1 문의**
                
                - 정렬 기준
                
                   - ORDER_NUM: 지정된 정렬 순서순(기본)
                
                   - ID: 최신 등록순
                
                   - IS_ANSWERED: 답변 여부
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | board | string | 게시판 | Y | "PRODUCT_INQUERY" |
                | category | string | 카테고리 | N | "PRODUCT_QUESTION" |
                | targetType | string | 대상 유형 | N | "PRICE_POLICY" |
                | targetId | number | 대상 ID(PRICE_POLICY ID) | N | 10 |
                | cursor | number | 커서(무한 스크롤) | N | 30 |
                | pageSize | number | 페이지 크기 | N | 10 |
                | sortDirection | string | 정렬 방향(DESC/ASC) | N | "DESC" |
                | sortProperty | string | 정렬 기준 | N | "ORDER_NUM" |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | HTTP 상태 코드 | 200 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-13T16:32:18.828188" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "게시글 목록 조회 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | totalElements | number | 총 게시글 수(첫 페이지에서만 반환) | 120 |
                | hasNext | boolean | 다음 페이지 존재 여부 | true |
                | nextCursor | number | 다음 페이지 커서 | 20 |
                | items | array | 게시글 목록 | [ ... ] |
                
                ---
                
                ### Response > content > items
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 게시글 ID | 10 |
                | userId | number | 작성자 ID | 5 |
                | parentId | number | 부모 게시글 ID | null |
                | board | string | 게시판 | "PRODUCT_INQUERY" |
                | category | string | 카테고리 | "PRODUCT_QUESTION" |
                | targetType | string | 대상 유형 | "PRICE_POLICY" |
                | targetId | number | 대상 ID | 120 |
                | writerName | string | 작성자명 | "홍길동" |
                | title | string | 제목 | "배송 문의" |
                | content | string | 내용 | "배송 언제 오나요?" |
                | isPrivate | boolean | 비밀글 여부 | true |
                | createdAt | string(datetime) | 생성 일시 | "2026-01-13T13:12:40.921092" |
                | modifiedAt | string(datetime) | 수정 일시 | "2026-01-13T13:12:40.921092" |
                | product | object | 상품 정보(상품 문의글이 아닌 경우 null) | { ... } |
                """,
        parameters = {
                @Parameter(
                        name = "board",
                        in = ParameterIn.QUERY,
                        required = true,
                        description = "게시판",
                        schema = @Schema(type = "string", example = "PRODUCT_INQUERY")
                ),
                @Parameter(
                        name = "category",
                        in = ParameterIn.QUERY,
                        description = "게시글 카테고리",
                        schema = @Schema(type = "string", example = "PRODUCT_QUESTION")
                ),
                @Parameter(
                        name = "targetType",
                        in = ParameterIn.QUERY,
                        description = "대상 유형",
                        schema = @Schema(type = "string", example = "PRICE_POLICY")
                ),
                @Parameter(
                        name = "targetId",
                        in = ParameterIn.QUERY,
                        description = "대상 ID",
                        schema = @Schema(type = "number", example = "10")
                ),
                @Parameter(
                        name = "cursor",
                        in = ParameterIn.QUERY,
                        description = "커서",
                        schema = @Schema(type = "number")
                ),
                @Parameter(
                        name = "pageSize",
                        in = ParameterIn.QUERY,
                        description = "페이지 크기",
                        schema = @Schema(type = "number", example = "10")
                ),
                @Parameter(
                        name = "sortDirection",
                        in = ParameterIn.QUERY,
                        description = "정렬 방향",
                        schema = @Schema(
                                type = "string",
                                example = "DESC",
                                allowableValues = {"ASC", "DESC"},
                                defaultValue = "DESC"
                        )
                ),
                @Parameter(
                        name = "sortProperty",
                        in = ParameterIn.QUERY,
                        description = "정렬 속성",
                        schema = @Schema(
                                type = "string",
                                example = "ORDER_NUM",
                                allowableValues = {"ORDER_NUM", "ID", "IS_ANSWERED"},
                                defaultValue = "ORDER_NUM"
                        )
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "게시글 목록 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-13T17:33:19.652324",
                                          "content": {
                                            "posts": {
                                              "totalElements": null,
                                              "hasNext": false,
                                              "nextCursor": 13,
                                              "items": [
                                                {
                                                  "id": 14,
                                                  "userId": 4,
                                                  "parentId": 1,
                                                  "board": "ONE_ON_ONE_INQUERY",
                                                  "category": "POINT",
                                                  "targetType": null,
                                                  "targetId": null,
                                                  "writerName": "홍*동",
                                                  "title": "게시글 제목",
                                                  "content": "게시글 내용",
                                                  "isPrivate": false,
                                                  "createdAt": "2026-01-13T17:17:29.943604",
                                                  "modifiedAt": "2026-01-13T17:17:29.955261",
                                                  "product": null,
                                                  "replies": []
                                                },
                                                {
                                                  "id": 13,
                                                  "userId": 4,
                                                  "parentId": 1,
                                                  "board": "ONE_ON_ONE_INQUERY",
                                                  "category": "RETURN_EXCHANGE",
                                                  "targetType": null,
                                                  "targetId": null,
                                                  "writerName": "홍*동",
                                                  "title": "게시글 제목",
                                                  "content": "게시글 내용",
                                                  "isPrivate": false,
                                                  "createdAt": "2026-01-13T17:17:21.374981",
                                                  "modifiedAt": "2026-01-13T17:17:21.403971",
                                                  "product": null,
                                                  "replies": []
                                                }
                                              ]
                                            }
                                          },
                                          "message": "게시글 목록 조회 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface GetPostsApiDocs {
}
