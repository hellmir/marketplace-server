package com.personal.marketnote.community.adapter.in.client.post.controller.apidocs;

import com.personal.marketnote.community.adapter.in.client.review.request.RegisterReviewRequest;
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
        summary = "게시글 등록",
        description = """
                작성일자: 2026-01-13
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 게시글을 등록합니다.
                
                - 게시판 및 카테고리 목록
                
                    - **"NOTICE": 공지**
                
                        - "ANNOUNCEMENT": 공지
                
                        - "EVENT": 이벤트
                
                    - **"FAQ": FAQ**
                
                        - "TOP_NOTICE": TOP 공지
                
                        - "MEMBER": 회원
                
                        - "ORDER_PAYMENT_BULK": 주문/결제/대량 주문
                
                        - "CANCEL_EXCHANGE_REFUND": 취소/교환/환불
                
                        - "DELIVERY": 배송
                
                        - "EVENT_COUPON_POINT": 이벤트/쿠폰/적립금
                
                        - "PRODUCT": 상품
                
                    - **"PRODUCT_INQUERY": 상품 문의**
                
                        - "PRODUCT_QUESTION": 상품 문의
                
                        - "RESTOCK": 재입고 문의
                
                        - "SHIPPING": 배송 문의
                
                    - **"ONE_ON_ONE_INQUERY": 1:1 문의**
                
                        - "ORDER_PAYMENT": 주문/결제
                
                        - "DELIVERY": 배송 관련
                
                        - "CANCEL_REFUND": 취소/환불
                
                        - "RETURN_EXCHANGE": 반품/교환
                
                        - "POINT": 적립금(포인트)
                
                        - "EVENT_COUPON": 이벤트/쿠폰
                
                - 대상 유형 목록
                
                    - "PRODUCT": 상품
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | parentId | number | 부모 게시글 ID (답변 시 사용) | N | 1 |
                | board | string | 대상 게시판 | Y | "NOTICE" |
                | category | string | 게시글 카테고리 | Y | "ANNOUNCEMENT" |
                | targetType | string | 대상 유형 | N | "PRODUCT" |
                | targetId | number | 대상 ID | N | 1 |
                | writerName | string | 작성자 이름 | Y | "홍길동" |
                | title | string | 제목 | Y | "게시글 제목" |
                | content | string | 내용 | Y | "게시글 내용" |
                | isPrivate | boolean | 비밀글 여부 | Y | false |
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | HTTP 상태 코드 | 201 |
                | code | string | 응답 코드 | "SUC01" |
                | timestamp | string(datetime) | 응답 일시 | "2026-01-09T16:32:18.828188" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "게시글 등록 성공" |
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 생성된 게시글 ID | 3 |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        requestBody = @RequestBody(
                required = true,
                content = @Content(
                        schema = @Schema(implementation = RegisterReviewRequest.class),
                        examples = @ExampleObject("""
                                {
                                  "parentId": 1,
                                  "board": "NOTICE",
                                  "category": "공지",
                                  "targetType": "PRODUCT",
                                  "targetId": 1,
                                  "writerName": "홍길동",
                                  "title": "게시글 제목",
                                  "content": "게시글 내용",
                                  "isPrivate": false
                                }
                                """)
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "게시글 등록 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 201,
                                          "code": "SUC01",
                                          "timestamp": "2026-01-13T16:39:31.057206",
                                          "content": {
                                            "id": 3
                                          },
                                          "message": "게시글 등록 성공"
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
                                          "timestamp": "2026-01-09T16:32:18.828188",
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
                                          "timestamp": "2026-01-09T16:32:18.828188",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "게시판에 맞지 않는 카테고리명 전송",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 400,
                                          "code": "BAD_REQUEST",
                                          "timestamp": "2026-01-13T16:43:07.990429",
                                          "content": null,
                                          "message": "게시판에 맞지 않는 카테고리입니다. 카테고리명: PRODUCT_QUESTION"
                                        }
                                        """)
                        )
                )
        }
)
public @interface RegisterPostApiDocs {
}
