package com.personal.marketnote.fulfillment.adapter.in.web.vendor.controller.apidocs;

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
@Operation(
        summary = "(관리자) 파스토 입고 목록 조회",
        description = """
                작성일자: 2026-02-03
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                파스토 상품 입고 목록을 조회합니다.
                
                ---
                
                ## Request
                
                | **키** | **위치** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- | --- |
                | accessToken | header | string | 파스토 액세스 토큰 | Y | 3169eb15ef7a11f0be620ab49498ff55 |
                | customerCode | path | string | 파스토 고객사 코드 | Y | 94388 |
                | startDate | path | string | 조회 시작일(YYYYMMDD) | Y | 20260112 |
                | endDate | path | string | 조회 종료일(YYYYMMDD) | Y | 20260112 |
                
                ---
                
                ## Response
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | statusCode | number | 상태 코드 | 200: 성공 / 400: 클라이언트 요청 오류 / 401: 인증 실패 / 403: 인가 실패 / 500: 그 외 |
                | code | string | 응답 코드 | "SUC01" / "BAD_REQUEST" / "UNAUTHORIZED" / "FORBIDDEN" / "INTERNAL_SERVER_ERROR" |
                | timestamp | string(datetime) | 응답 일시 | "2026-02-03T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "파스토 상품 입고 목록 조회 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | dataCount | number | 조회 건수 | 1 |
                | warehousing | array | 입고 목록 | [ ... ] |
                
                ---
                
                ### Response > content > warehousing
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | ordDt | string | 입고 요청일 | "20260112" |
                | whCd | string | 창고 코드 | "TEST" |
                | whNm | string | 창고명 | "테스트" |
                | slipNo | string | 입고 요청번호 | "TESTIO260112000003" |
                | ordNo | string | 주문번호 | "" |
                | cstCd | string | 고객사 코드 | "94388" |
                | cstNm | string | 고객사명 | "마켓노트 주식회사 테스트" |
                | supCd | string | 공급사 코드 | "99999999" |
                | cstSupCd | string | 고객사 공급사 코드 | null |
                | supNm | string | 공급사명 | "미지정 공급사" |
                | sku | number | SKU 수량 | 1 |
                | ordQty | number | 주문수량 | 1 |
                | inQty | number | 입고수량 | 0 |
                | tarQty | number | 대상수량 | 1 |
                | inWay | string | 입고방법 코드 | "01" |
                | inWayNm | string | 입고방법명 | "택배" |
                | parcelComp | string | 택배사명 | "" |
                | parcelInvoiceNo | string | 송장번호 | "" |
                | wrkStat | string | 작업상태 코드 | "1" |
                | wrkStatNm | string | 작업상태명 | "입고요청" |
                | emgrYn | string | 긴급 여부 | null |
                | remark | string | 비고 | "" |
                | goodsSerialNo | array | 상품 일련번호 목록 | [] |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "accessToken",
                        description = "파스토 액세스 토큰",
                        in = ParameterIn.HEADER,
                        required = true,
                        schema = @Schema(type = "string", example = "3169eb15ef7a11f0be620ab49498ff55")
                ),
                @Parameter(
                        name = "customerCode",
                        description = "파스토 고객사 코드",
                        in = ParameterIn.PATH,
                        required = true,
                        schema = @Schema(type = "string", example = "94388")
                ),
                @Parameter(
                        name = "startDate",
                        description = "조회 시작일(YYYYMMDD)",
                        in = ParameterIn.PATH,
                        required = true,
                        schema = @Schema(type = "string", example = "20260112")
                ),
                @Parameter(
                        name = "endDate",
                        description = "조회 종료일(YYYYMMDD)",
                        in = ParameterIn.PATH,
                        required = true,
                        schema = @Schema(type = "string", example = "20260112")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "파스토 상품 입고 목록 조회 성공",
                        content = @Content(
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-02-03T12:12:30.013",
                                          "content": {
                                            "dataCount": 1,
                                            "warehousing": [
                                              {
                                                "ordDt": "20260112",
                                                "whCd": "TEST",
                                                "whNm": "테스트",
                                                "slipNo": "TESTIO260112000003",
                                                "ordNo": "",
                                                "cstCd": "94388",
                                                "cstNm": "마켓노트 주식회사 테스트",
                                                "supCd": "99999999",
                                                "cstSupCd": null,
                                                "supNm": "미지정 공급사",
                                                "sku": 1,
                                                "ordQty": 1,
                                                "inQty": 0,
                                                "tarQty": 1,
                                                "inWay": "01",
                                                "inWayNm": "택배",
                                                "parcelComp": "",
                                                "parcelInvoiceNo": "",
                                                "wrkStat": "1",
                                                "wrkStatNm": "입고 요청",
                                                "emgrYn": null,
                                                "remark": "",
                                                "goodsSerialNo": []
                                              }
                                            ]
                                          },
                                          "message": "파스토 상품 입고 목록 조회 성공"
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
                                          "timestamp": "2026-02-03T12:12:30.013",
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
                                          "timestamp": "2026-02-03T12:12:30.013",
                                          "content": null,
                                          "message": "Access Denied"
                                        }
                                        """)
                        )
                )
        })
public @interface GetFasstoWarehousingApiDocs {
}
