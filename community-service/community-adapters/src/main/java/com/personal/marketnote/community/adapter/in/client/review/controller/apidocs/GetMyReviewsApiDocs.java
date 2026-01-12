package com.personal.marketnote.community.adapter.in.client.review.controller.apidocs;

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
        summary = "나의 리뷰 목록 조회",
        description = """
                작성일자: 2026-01-12
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 나의 리뷰 목록을 조회합니다.
                
                - 페이로드에 cursor 값이 없는 경우(첫 페이지): 총 리뷰 개수 반환 O
                
                - 페이로드에 cursor 값이 있는 경우(더 보기): 총 리뷰 개수 반환 X
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | cursor | number | 이전 페이지의 nextCursor 값, 전송하지 않는 경우 첫 데이터부터 조회 | N | 1 |
                | pageSize | number | 페이지 크기 | N | 4 |
                | sortDirection | string | 정렬 방향(ASC, DESC) | N | DESC |
                | sortProperty | string | 정렬 속성(ID, ORDER_NUM) | N | ID |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | HTTP 상태 코드 | 201 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-12T16:32:18.828188" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "나의 리뷰 목록 조회 성공" |
                
                ---
                
                ### Response > content
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | reviews | array | 리뷰 목록 | [ ... ] |
                
                ---
                
                ### Response > content > reviews
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | totalElements | number | 총 리뷰 개수 | 100 |
                | hasNext | boolean | 다음 페이지 존재 여부 | true |
                | nextCursor | number | 현재 페이지에서 조회한 마지막 리소스의 식별자 | 18 |
                | items | array | 리뷰 목록 | [ ... ] |
                
                ---
                
                ### Response > content > reviews > items
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 리뷰 ID | 3 |
                | reviewerId | number | 리뷰어 ID | 17 |
                | orderId | number | 주문 ID | 1 |
                | productId | number | 상품 ID | 1 |
                | pricePolicyId | number | 가격 정책 ID | 11 |
                | selectedOptions | string | 선택된 옵션 목록 | "30개입, 5박스" |
                | quantity | number | 주문 수량 | 2 |
                | reviewerName | string | 리뷰 작성자 이름 | "홍*동" |
                | rating | number | 평점 | 4 |
                | content | string | 리뷰 내용 | "배송이 빠르고 포장 상태도 좋았습니다." |
                | isPhoto | boolean | 포토 리뷰 여부 | false |
                | isEdited | boolean | 수정 여부 | false |
                | likeCount | number | 좋아요 개수 | 0 |
                | isUserLiked | boolean | 로그인 사용자가 좋아요를 눌렀는지 여부 | false |
                | status | string | 상태 | "ACTIVE" |
                | createdAt | string(datetime) | 생성 일시 | "2026-01-12T16:57:59.792312" |
                | modifiedAt | string(datetime) | 수정 일시 | "2026-01-12T16:57:59.805601" |
                | orderNum | number | 정렬 순서 | 1 |
                """,
        parameters = {
                @Parameter(
                        name = "cursor",
                        in = ParameterIn.QUERY,
                        description = "이전 페이지의 nextCursor 값, 전송하지 않는 경우 첫 데이터부터 조회",
                        schema = @Schema(type = "number")
                ),
                @Parameter(
                        name = "pageSize",
                        in = ParameterIn.QUERY,
                        description = "페이지 크기",
                        schema = @Schema(
                                type = "number",
                                example = "4",
                                defaultValue = "4"
                        )
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
                                example = "ID",
                                allowableValues = {"ID", "ORDER_NUM"},
                                defaultValue = "ID"
                        )
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "나의 리뷰 목록 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-12T16:46:56.165291",
                                          "content": {
                                            "reviews": {
                                              "totalElements": 28,
                                              "hasNext": true,
                                              "nextCursor": 27,
                                              "items": [
                                                {
                                                  "id": 30,
                                                  "reviewerId": 17,
                                                  "orderId": 2,
                                                  "productId": 1,
                                                  "pricePolicyId": 159,
                                                  "selectedOptions": "30개입, 5박스",
                                                  "quantity": 2,
                                                  "reviewerName": "홍*동",
                                                  "rating": 5,
                                                  "content": "배송이 빠르고 포장 상태도 좋았습니다.",
                                                  "isPhoto": false,
                                                  "isEdited": false,
                                                  "likeCount": 0,
                                                  "isUserLiked": false,
                                                  "status": "ACTIVE",
                                                  "createdAt": "2026-01-12T15:22:05.793454",
                                                  "modifiedAt": "2026-01-12T15:22:05.944009",
                                                  "orderNum": 30
                                                },
                                                {
                                                  "id": 29,
                                                  "reviewerId": 17,
                                                  "orderId": 14,
                                                  "productId": 1,
                                                  "pricePolicyId": 159,
                                                  "selectedOptions": "30개입, 5박스",
                                                  "quantity": 2,
                                                  "reviewerName": "홍*동",
                                                  "rating": 5,
                                                  "content": "배송이 빠르고 포장 상태도 좋았습니다.",
                                                  "isPhoto": false,
                                                  "isEdited": false,
                                                  "likeCount": 0,
                                                  "isUserLiked": false,
                                                  "status": "ACTIVE",
                                                  "createdAt": "2026-01-12T15:21:52.268077",
                                                  "modifiedAt": "2026-01-12T15:21:52.796582",
                                                  "orderNum": 29
                                                },
                                                {
                                                  "id": 28,
                                                  "reviewerId": 17,
                                                  "orderId": 3331,
                                                  "productId": 1,
                                                  "pricePolicyId": 11,
                                                  "selectedOptions": "30개입, 5박스",
                                                  "quantity": 2,
                                                  "reviewerName": "홍*동",
                                                  "rating": 5,
                                                  "content": "배송이 빠르고 포장 상태도 좋았습니다.",
                                                  "isPhoto": true,
                                                  "isEdited": false,
                                                  "likeCount": 0,
                                                  "isUserLiked": false,
                                                  "status": "ACTIVE",
                                                  "createdAt": "2026-01-10T16:18:16.997939",
                                                  "modifiedAt": "2026-01-10T16:18:17.086802",
                                                  "orderNum": 28
                                                },
                                                {
                                                  "id": 27,
                                                  "reviewerId": 17,
                                                  "orderId": 333,
                                                  "productId": 1,
                                                  "pricePolicyId": 11,
                                                  "selectedOptions": "30개입, 5박스",
                                                  "quantity": 2,
                                                  "reviewerName": "홍*동",
                                                  "rating": 5,
                                                  "content": "배송이 빠르고 포장 상태도 좋았습니다.",
                                                  "isPhoto": false,
                                                  "isEdited": false,
                                                  "likeCount": 0,
                                                  "isUserLiked": false,
                                                  "status": "ACTIVE",
                                                  "createdAt": "2026-01-10T16:18:06.013009",
                                                  "modifiedAt": "2026-01-10T16:18:06.220729",
                                                  "orderNum": 27
                                                }
                                              ]
                                            }
                                          },
                                          "message": "나의 리뷰 목록 조회 성공"
                                        }
                                        """)
                        )
                )
        }
)
public @interface GetMyReviewsApiDocs {
}
