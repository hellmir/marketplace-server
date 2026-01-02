package com.personal.marketnote.product.adapter.in.client.product.controller.apidocs;

import com.personal.marketnote.common.adapter.in.api.schema.StringResponseSchema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(summary = "상품 목록 조회", description = """
        작성일자: 2026-01-01
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 상품 목록을 조회합니다.
        
        - categoryId 미 전송 시 전체 상품 목록을 반환합니다.
        
        - categoryId 전송 시 해당 카테고리의 상품 목록을 반환합니다.
        
        - 페이로드에 cursor 값이 없는 경우(첫 페이지): 총 상품 개수 반환 O
        
        - 페이로드에 cursor 값이 있는 경우(더 보기): 총 상품 개수 반환 X
        
        - 옵션 별 상품 목록 조회 설정된 상품은 모든 옵션 카테고리 조합에 대해 개별 상품으로 조회됩니다.
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | categoryId | number | 카테고리 ID | N | 1001 |
        | cursor | number | 이전 페이지의 nextCursor 값, 전송하지 않는 경우 첫 데이터부터 조회 | N | -1 |
        | page-size | number | 페이지 크기 | N | 4 |
        | sortDirection | string | 정렬 방향(ASC, DESC) | N | DESC |
        | sortProperty | string | 정렬 속성(ORDER_NUM, POPULARITY, DISCOUNT_PRICE, ACCUMULATED_POINT) | N | ORDER_NUM |
        | searchTarget | string | 검색 대상(NAME, BRAND_NAME) | N | NAME |
        | searchKeyword | string | 검색 키워드 | N | "노트왕" |
        
        ---
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
        | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
        | timestamp | string(datetime) | 응답 일시 | "2026-01-01T10:37:32.320824" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 목록 조회 성공" |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | products | array | 상품 목록 | [ ... ] |
        | nextCursor | number | 현재 페이지에서 조회한 마지막 리소스의 식별자 | 18 |
        | hasNext | boolean | 다음 페이지 존재 여부 | true |
        | totalElements | number | 총 아이템 수 | 30 |
        
        ---
        
        ### Response > content > products
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 ID | 1 |
        | sellerId | number | 판매자 ID | 10 |
        | name | string | 상품명 | "스프링노트1" |
        | brandName | string | 브랜드명 | "노트왕" |
        | price | number | 기본 판매 가격(원) | 100000 |
        | discountPrice | number | 할인 판매 가격(원) | 80000 |
        | discountRate | number | 할인율(%, 최대 소수점 1자리) | 20 |
        | accumulatedPoint | number | 구매 시 적립 포인트 | 1000 |
        | sales | number | 판매량 | 0 |
        | productTags | array | 상품 태그 목록 | [ ... ] |
        | orderNum | number | 정렬 순서 | 1 |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        ### Response > content > products > productTags
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 태그 ID | 1 |
        | productId | number | 상품 ID | 10 |
        | name | string | 상품 태그명 | "루테인" |
        | orderNum | number | 정렬 순서 | 1 |
        | status | string | 상태 | "ACTIVE" |
        """, security = {
        @SecurityRequirement(name = "bearer")
},
        parameters = {
                @Parameter(
                        name = "categoryId",
                        in = ParameterIn.QUERY,
                        description = "카테고리 ID(없는 경우 전체 조회)",
                        schema = @Schema(type = "number")
                ),
                @Parameter(
                        name = "cursor",
                        in = ParameterIn.QUERY,
                        description = "이전 페이지의 nextCursor 값, 전송하지 않는 경우 첫 데이터부터 조회",
                        schema = @Schema(type = "number")
                ),
                @Parameter(
                        name = "page-size",
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
                                example = "ORDER_NUM",
                                allowableValues = {"ORDER_NUM", "POPULARITY", "DISCOUNT_PRICE", "ACCUMULATED_POINT"},
                                defaultValue = "ORDER_NUM"
                        )
                ),
                @Parameter(
                        name = "searchTarget",
                        in = ParameterIn.QUERY,
                        description = "검색 대상",
                        schema = @Schema(
                                type = "string",
                                allowableValues = {"NAME", "BRAND_NAME"},
                                defaultValue = "NAME"
                        )
                ),
                @Parameter(
                        name = "searchKeyword",
                        in = ParameterIn.QUERY,
                        description = "검색 키워드(없는 경우 전체 조회)",
                        schema = @Schema(type = "string")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "조회 성공",
                        content = @Content(
                                schema = @Schema(implementation = StringResponseSchema.class),
                                examples = @ExampleObject("""
                                                                                {
                                                                                  "statusCode": 200,
                                                                                  "code": "SUC01",
                                                                                  "timestamp": "2026-01-01T10:34:29.332952183",
                                                                                  "content": {
                                                                                    "products": {
                                                                                      "totalElements": null,
                                                                                      "hasNext": true,
                                                                                      "nextCursor": 19,
                                                                                      "items": [
                                                                                        {
                                                                                          "id": 20,
                                                                                          "sellerId": 12,
                                                                                          "name": "스프링노트2",
                                                                                          "brandName": "노트킹",
                                                                                          "price": 45000,
                                                                                          "discountPrice": 20000,
                                                                                          "discountRate": 17.8,
                                                                                          "accumulatedPoint": 1500,
                                                                                          "sales": 0,
                                                                                          "productTags": [],
                                                                                          "orderNum": 20,
                                                                                          "status": "ACTIVE"
                                                                                        },
                                                                                        {
                                                                                          "id": 19,
                                                                                          "sellerId": 1,
                                                                                          "name": "스프링노트1 (1박스 / 30개입)",
                                                                                          "brandName": "노트왕",
                                                                                          "price": 92000,
                                                                                          "discountPrice": 84000,
                                                                                          "discountRate": 17.8,
                                                                                          "accumulatedPoint": 7400,
                                                                                          "sales": 0,
                                                                                          "productTags": [],
                                                                                          "orderNum": 19,
                                                                                          "status": "ACTIVE"
                                                                                        },
                                                                                        {
                                                                                          "id": 19,
                                                                                          "sellerId": 1,
                                                                                          "name": "스프링노트1 (1박스 / 60개입)",
                                                                                          "brandName": "노트왕",
                                                                                          "price": 102000,
                                                                                          "discountPrice": 94000,
                                                                                          "discountRate": 17.8,
                                                                                          "accumulatedPoint": 12400,
                                                                                          "sales": 0,
                                                                                          "productTags": [],
                                                                                          "orderNum": 19,
                                                                                          "status": "ACTIVE"
                                                                                        },
                                                                                        {
                                                                                          "id": 19,
                                                                                          "sellerId": 1,
                                                                                          "name": "스프링노트1 (3박스 / 30개입)",
                                                                                          "brandName": "노트왕",
                                                                                          "price": 154000,
                                                                                          "discountPrice": 146000,
                                                                                          "discountRate": 17.8,
                                                                                          "accumulatedPoint": 8200,
                                                                                          "sales": 0,
                                                                                          "productTags": [],
                                                                                          "orderNum": 19,
                                        탭                                                  "status": "ACTIVE"
                                                                                        }
                                                                                      ]
                                                                                    }
                                                                                  },
                                                                                  "message": "상품 목록 조회 성공"
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
                                          "timestamp": "2025-12-29T10:19:52.558748",
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
                                          "timestamp": "2025-12-29T10:19:52.558748",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface GetProductsApiDocs {
}
