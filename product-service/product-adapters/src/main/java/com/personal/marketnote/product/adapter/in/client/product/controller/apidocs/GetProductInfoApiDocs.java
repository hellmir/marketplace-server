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
@Operation(summary = "상품 상세 정보 조회", description = """
        작성일자: 2026-01-02
        
        작성자: 성효빈
        
        ---
        
        ## Description
        
        - 상품의 상세 정보를 조회합니다.
        
        - Redis Cache 적용되어 있습니다. TTL: 10분
        
        ---
        
        ## Request
        
        | **키** | **타입** | **설명** | **필수 여부** | **예시** |
        | --- | --- | --- | --- | --- |
        | id | number | 상품 ID | Y | 1 |
        | selectedOptionIds | array<number> | 선택된 옵션 ID 목록 | N | [4, 7] |
        
        ## Response
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 404: 리소스 조회 실패 / 409: 충돌 / 500: 그 외 |
        | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "NOT_FOUND" / "CONFLICT" / "INTERNAL_SERVER_ERROR" |
        | timestamp | string(datetime) | 응답 일시 | "2026-01-02T10:37:32.320824" |
        | content | object | 응답 본문 | { ... } |
        | message | string | 처리 결과 | "상품 상세 정보 조회 성공" |
        
        ---
        
        ### Response > content
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | productInfo | object | 상품 상세 정보 | { ... } |
        | categories | array | 옵션 카테고리 목록 | [ ... ] |
        | representativeImages | object | 상품 상세 정보 상단 대표 이미지 목록 | { ... } |
        | contentImages | object | 상품 상세 정보 본문 이미지 목록 | { ... } |
        | selectedOptionIds | array<number> | 선택된 옵션 ID 목록 | [4, 7] |
        
        ---
        
        ### Response > content > productInfo
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 ID | 19 |
        | sellerId | number | 판매자 ID | 1 |
        | name | string | 상품명 | "스프링노트1" |
        | brandName | string | 브랜드명 | "노트왕" |
        | detail | string | 상품 상세 설명 | "스프링노트1 설명" |
        | price | number | 기본 판매 가격(원) | 164000 |
        | discountPrice | number | 할인 판매 가격(원) | 156000 |
        | discountRate | number | 할인율(%, 최대 소수점 1자리) | 17.8 |
        | accumulatedPoint | number | 구매 시 적립 포인트 | 13200 |
        | sales | number | 판매량 | 0 |
        | viewCount | number | 조회수 | 0 |
        | popularity | number | 인기도 | 0 |
        | findAllOptionsYn | boolean | 모든 옵션 선택 여부 | true |
        | productTags | array | 상품 태그 목록 | [ ... ] |
        | orderNum | number | 정렬 순서 | 19 |
        | status | string | 상태 | "ACTIVE" |
        ---
        
        ### Response > content > productInfo > productTags
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 상품 태그 ID | 9 |
        | productId | number | 상품 ID | 19 |
        | name | string | 상품 태그명 | "루테인" |
        | orderNum | number | 정렬 순서 | null |
        | status | string | 상태 | "ACTIVE" |
        
        ---
        
        ### Response > content > categories
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 옵션 카테고리 ID | 2 |
        | name | string | 옵션 카테고리명 | "수량" |
        | orderNum | number | 정렬 순서 | 2 |
        | status | string | 상태 | "ACTIVE" |
        | options | array | 하위 옵션 목록 | [ ... ] |
        
        ---
        
        ### Response > content > categories > options
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 옵션 ID | 3 |
        | content | string | 옵션 내용 | "1박스" |
        | price | number | 옵션 가격(원) | 37000 |
        | accumulatedPoint | number | 적립 포인트 | 1200 |
        | status | string | 상태 | "ACTIVE" |
        | isSelected | boolean | 선택 여부 | false |
        
        ---
        
        ### Response > content > representativeImages
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | images | array | 상단 대표 이미지 목록 | [ ... ] |
        
        ---
        
        ### Response > content > representativeImages > images
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 이미지 ID | 1 |
        | sort | string | 이미지 종류 | "PRODUCT_REPRESENTATIVE_IMAGE" |
        | extension | string | 이미지 확장자 | "png" |
        | name | string | 이미지명 | "상품대표이미지1" |
        | s3Url | string | 이미지 S3 URL | "https://marketnote.s3.amazonaws.com/product/30/1763534195681_image.png" |
        | resizedS3Urls | array | 리사이즈 이미지 S3 URL 목록 | [ "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png", "https://marketnote.s3.amazonaws.com/product/30/1763534195988_image_800.png" ] |
        | orderNum | number | 정렬 순서 | 41 |
        
        ---
        
        ### Response > content > contentImages
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | images | array | 본문 이미지 목록 | [ ... ] |
        
        ---
        
        ### Response > content > contentImages > images
        
        | **키** | **타입** | **설명** | **예시** |
        | --- | --- | --- | --- |
        | id | number | 이미지 ID | 1 |
        | sort | string | 이미지 종류 | "PRODUCT_CONTENT_IMAGE" |
        | extension | string | 이미지 확장자 | "jpg" |
        | name | string | 이미지명 | "상품본문이미지1" |
        | s3Url | string | 이미지 S3 URL | "https://marketnote.s3.amazonaws.com/product/30/1763534195623_image.png" |
        | resizedS3Urls | array | 리사이즈 이미지 S3 URL 목록 | [] |
        | orderNum | number | 정렬 순서 | 40 |
        """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "id",
                        in = ParameterIn.PATH,
                        description = "상품 ID",
                        schema = @Schema(type = "number", example = "1")
                ),
                @Parameter(
                        name = "selectedOptionIds",
                        in = ParameterIn.QUERY,
                        description = "선택된 옵션 ID 목록"
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
                                          "timestamp": "2026-01-04T15:36:46.169111",
                                          "content": {
                                            "productInfo": {
                                              "id": 30,
                                              "sellerId": 1,
                                              "name": "건기식테스트1",
                                              "brandName": "노트왕",
                                              "detail": "건기식테스트건기식테스트건기식테스트",
                                              "price": 60000,
                                              "discountPrice": 40000,
                                              "discountRate": 33.3,
                                              "accumulatedPoint": 3000,
                                              "sales": 0,
                                              "viewCount": 0,
                                              "popularity": 0,
                                              "findAllOptionsYn": true,
                                              "productTags": [
                                                {
                                                  "id": 13,
                                                  "productId": 30,
                                                  "name": "루테인",
                                                  "orderNum": null,
                                                  "status": "ACTIVE"
                                                },
                                                {
                                                  "id": 14,
                                                  "productId": 30,
                                                  "name": "아스타잔틴",
                                                  "orderNum": null,
                                                  "status": "ACTIVE"
                                                }
                                              ],
                                              "orderNum": 30,
                                              "status": "ACTIVE"
                                            },
                                            "categories": [
                                              {
                                                "id": 4,
                                                "name": "수량",
                                                "orderNum": 4,
                                                "status": "ACTIVE",
                                                "options": [
                                                  {
                                                    "id": 8,
                                                    "content": "1박스",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  },
                                                  {
                                                    "id": 9,
                                                    "content": "3박스",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  }
                                                ]
                                              },
                                              {
                                                "id": 5,
                                                "name": "개당 수량",
                                                "orderNum": 5,
                                                "status": "ACTIVE",
                                                "options": [
                                                  {
                                                    "id": 10,
                                                    "content": "30개입",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  },
                                                  {
                                                    "id": 11,
                                                    "content": "60개입",
                                                    "price": null,
                                                    "accumulatedPoint": null,
                                                    "status": "ACTIVE",
                                                    "isSelected": false
                                                  }
                                                ]
                                              }
                                            ],
                                            "representativeImages": {
                                              "images": [
                                                {
                                                  "id": 41,
                                                  "sort": "PRODUCT_REPRESENTATIVE_IMAGE",
                                                  "extension": "png",
                                                  "name": "상품대표이미지1",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763534195681_image.png",
                                                  "resizedS3Urls": [
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763534195922_image_600.png",
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763534195988_image_800.png"
                                                  ],
                                                  "orderNum": 41
                                                },
                                                {
                                                  "id": 39,
                                                  "sort": "PRODUCT_REPRESENTATIVE_IMAGE",
                                                  "extension": "png",
                                                  "name": "상품대표이미지2",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763534193377_image.png",
                                                  "resizedS3Urls": [
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763534193688_image_600.png",
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763534193761_image_800.png"
                                                  ],
                                                  "orderNum": 39
                                                },
                                                {
                                                  "id": 37,
                                                  "sort": "PRODUCT_REPRESENTATIVE_IMAGE",
                                                  "extension": "png",
                                                  "name": "상품대표이미지3",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533915880_image.png",
                                                  "resizedS3Urls": [
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533916081_image_600.png",
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533916187_image_800.png"
                                                  ],
                                                  "orderNum": 37
                                                },
                                                {
                                                  "id": 35,
                                                  "sort": "PRODUCT_REPRESENTATIVE_IMAGE",
                                                  "extension": "png",
                                                  "name": "상품대표이미지4",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533914726_image.png",
                                                  "resizedS3Urls": [
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533914954_image_600.png",
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533915038_image_800.png"
                                                  ],
                                                  "orderNum": 35
                                                },
                                                {
                                                  "id": 33,
                                                  "sort": "PRODUCT_REPRESENTATIVE_IMAGE",
                                                  "extension": "png",
                                                  "name": "상품대표이미지5",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533911501_image.png",
                                                  "resizedS3Urls": [
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533911804_image_600.png",
                                                    "https://marketnote.s3.amazonaws.com/product/30/1763533911854_image_800.png"
                                                  ],
                                                  "orderNum": 33
                                                }
                                              ]
                                            },
                                            "contentImages": {
                                              "images": [
                                                {
                                                  "id": 40,
                                                  "sort": "PRODUCT_CONTENT_IMAGE",
                                                  "extension": "jpg",
                                                  "name": "상품본문이미지1",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763534195623_image.png",
                                                  "resizedS3Urls": [],
                                                  "orderNum": 40
                                                },
                                                {
                                                  "id": 38,
                                                  "sort": "PRODUCT_CONTENT_IMAGE",
                                                  "extension": "jpg",
                                                  "name": "상품본문이미지2",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763534193258_image.png",
                                                  "resizedS3Urls": [],
                                                  "orderNum": 38
                                                },
                                                {
                                                  "id": 36,
                                                  "sort": "PRODUCT_CONTENT_IMAGE",
                                                  "extension": "jpg",
                                                  "name": "상품본문이미지3",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533915837_image.png",
                                                  "resizedS3Urls": [],
                                                  "orderNum": 36
                                                },
                                                {
                                                  "id": 34,
                                                  "sort": "PRODUCT_CONTENT_IMAGE",
                                                  "extension": "jpg",
                                                  "name": "상품본문이미지4",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533914674_image.png",
                                                  "resizedS3Urls": [],
                                                  "orderNum": 34
                                                },
                                                {
                                                  "id": 32,
                                                  "sort": "PRODUCT_CONTENT_IMAGE",
                                                  "extension": "jpg",
                                                  "name": "상품본문이미지5",
                                                  "s3Url": "https://marketnote.s3.amazonaws.com/product/30/1763533911286_image.png",
                                                  "resizedS3Urls": [],
                                                  "orderNum": 32
                                                }
                                              ]
                                            }
                                          },
                                          "message": "상품 상세 정보 조회 성공"
                                        }
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
                                          "timestamp": "2025-12-31T12:00:00.000",
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
                                          "timestamp": "2025-12-31T12:00:00.000",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "리소스 조회 실패",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 404,
                                          "code": "NOT_FOUND",
                                          "timestamp": "2026-01-02T13:32:26.542724",
                                          "content": null,
                                          "message": "상품을 찾을 수 없습니다. 전송된 상품 ID: 1"
                                        }
                                        """)
                        )
                )
        })
public @interface GetProductInfoApiDocs {
}
