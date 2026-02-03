package com.personal.marketnote.product.adapter.in.web.product.controller.apidocs;

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
@Operation(
        summary = "(관리자) 상품 목록 조회(파스토 연동)",
        description = """
                작성일자: 2026-02-03
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 관리자 페이지에서 상품 목록을 조회합니다.
                
                - 상품 항목마다 파스토 연동 정보를 함께 반환합니다.
                
                - 파스토 상품 정보는 cstGodCd(고객사상품코드)와 상품 ID를 기준으로 매칭합니다.
                
                - 페이로드에 cursor 값이 없는 경우(첫 페이지): 총 상품 개수 반환 O
                
                - 페이로드에 cursor 값이 있는 경우(더 보기): 총 상품 개수 반환 X
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | categoryId | number | 카테고리 ID | N | 1001 |
                | pricePolicyIds | array<number> | 가격 정책 ID 목록 | N | 1, 2 |
                | cursor | number | 이전 페이지의 nextCursor 값, 전송하지 않는 경우 첫 데이터부터 조회 | N | 1 |
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
                | timestamp | string(datetime) | 응답 일시 | "2026-02-03T12:12:30.013" |
                | content | object | 응답 본문 | { ... } |
                | message | string | 처리 결과 | "관리자 상품 목록 조회 성공" |
                
                ---
                
                ### Response > content
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | products | object | 상품 목록(CursorResponse) | { ... } |
                
                ---
                
                ### Response > content > products
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | totalElements | number | 총 아이템 수 | 30 |
                | nextCursor | number | 현재 페이지에서 조회한 마지막 리소스의 식별자 | 18 |
                | hasNext | boolean | 다음 페이지 존재 여부 | true |
                | items | array | 상품 목록 | [ ... ] |
                
                ---
                
                ### Response > content > products > items
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 상품 ID | 1 |
                | sellerId | number | 판매자 ID | 10 |
                | name | string | 상품명 | "스프링노트1" |
                | brandName | string | 브랜드명 | "노트왕" |
                | pricePolicy | object | 가격 정책 | { ... } |
                | sales | number | 판매량 | 0 |
                | productTags | array | 상품 태그 목록 | [ ... ] |
                | catalogImage | object | 상품 카탈로그 이미지 | { ... } |
                | selectedOptions | array | 선택된 옵션 목록 | [ ... ] |
                | stock | number | 재고 수량(재고 서버와 통신 실패한 경우 -1 반환) | 10 |
                | averageRating | number | 평점 평균 | 4.5 |
                | totalCount | number | 리뷰 총 개수 | 8 |
                | status | string | 상태 | "ACTIVE" |
                | orderNum | number | 정렬 순서 | 1 |
                | fasstoGoods | object | 파스토 상품 정보(매칭 없으면 null) | { ... } |
                
                ---
                
                ### Response > content > products > items > pricePolicy
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 가격 정책 ID | 22 |
                | price | number | 기본 판매 가격(원) | 50000 |
                | discountPrice | number | 할인 가격(원) | 40000 |
                | accumulatedPoint | number | 적립 포인트 | 2000 |
                | discountRate | number | 할인율(%, 최대 소수점 1자리) | 20 |
                | optionIds | array<number> | 옵션 ID 목록 | [8, 11] |
                
                ---
                
                ### Response > content > products > items > productTags
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 상품 태그 ID | 1 |
                | productId | number | 상품 ID | 10 |
                | name | string | 상품 태그명 | "루테인" |
                | orderNum | number | 정렬 순서 | 1 |
                | status | string | 상태 | "ACTIVE" |
                
                ---
                
                ### Response > content > products > items > catalogImage
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 이미지 ID | 1 |
                | sort | string | 이미지 종류 | "PRODUCT_CATALOG_IMAGE" |
                | extension | string | 이미지 확장자 | "jpg" |
                | name | string | 이미지명 | "스프링노트1" |
                | s3Url | string | 이미지 S3 URL | "https://marketnote.s3.amazonaws.com/product/30/1763517082648_grafana-icon.png" |
                | resizedS3Urls | array | 리사이즈 이미지 S3 URL 목록 | [ "https://marketnote.s3.amazonaws.com/product/30/1763517083251_grafana-icon_300x300.png" ] |
                | orderNum | number | 정렬 순서 | 1 |
                
                ---
                
                ### Response > content > products > items > selectedOptions
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | id | number | 옵션 ID | 1 |
                | content | string | 옵션 내용 | "1박스" |
                | status | string | 상태 | "ACTIVE" |
                
                ---
                
                ### Response > content > products > items > fasstoGoods
                
                | **키** | **타입** | **설명** | **예시** |
                | --- | --- | --- | --- |
                | godCd | string | 상품코드 | "943881" |
                | godType | string | 상품유형 | "1" |
                | godNm | string | 상품명 | "테스트 상품1" |
                | godTypeNm | string | 상품유형명 | "단품" |
                | invGodNmUseYn | string | 송장출력용 상품명 사용여부 | "N" |
                | cstGodCd | string | 고객사상품코드 | "1" |
                | godOptCd1 | string | 상품옵션코드1 | "" |
                | godOptCd2 | string | 상품옵션코드2 | "" |
                | cstCd | string | 고객사코드 | "94388" |
                | cstNm | string | 고객사명 | "마켓노트 주식회사 테스트" |
                | supCd | string | 공급사코드 | "" |
                | supNm | string | 공급사명 | null |
                | cateCd | string | 카테고리코드 | "" |
                | cateNm | string | 카테고리명 | null |
                | seasonCd | string | 계절상품 코드 | "0" |
                | genderCd | string | 성별상품 코드 | "A" |
                | godPr | string | 단가 | "0" |
                | inPr | string | 공급가 | "0" |
                | salPr | string | 판매가 | "0" |
                | dealTemp | string | 취급온도 | "03" |
                | pickFac | string | 피킹설비 | "" |
                | giftDiv | string | 사은품구분 | "01" |
                | giftDivNm | string | 상품구분명 | "본품" |
                | godWidth | string | 상품가로길이 | "0.000" |
                | godLength | string | 상품세로길이 | "0.000" |
                | godHeight | string | 상품높이길이 | "0.000" |
                | makeYr | string | 연식 | "" |
                | godBulk | string | 상품체적 | "0" |
                | godWeight | string | 상품무게 | "0" |
                | godSideSum | string | 상품규격(3면의합) | "0.000" |
                | godVolume | string | 상품중량 | "" |
                | godBarcd | string | 상품바코드 | "" |
                | boxWidth | string | 내품BOX가로길이 | "0" |
                | boxLength | string | 내품BOX세로길이 | "0" |
                | boxHeight | string | 내품BOX높이길이 | "0" |
                | boxBulk | string | 내품BOX체적 | "0" |
                | boxWeight | string | 내품BOX무게 | "0" |
                | inBoxBarcd | string | 내품BOX바코드 | null |
                | inBoxLength | string | 입고BOX세로길이 | "0" |
                | inBoxHeight | string | 입고BOX높이길이 | "0" |
                | inBoxBulk | string | 입고BOX체적 | "0" |
                | inBoxWidth | string | 내품BOX가로길이 | "0" |
                | inBoxWeight | string | 입고BOX무게 | "0" |
                | inBoxSideSum | string | 입고BOX규격(3면의합) | "0" |
                | boxInCnt | string | 내품박스입수 | "0" |
                | inBoxInCnt | string | 입고박스입수 | "0" |
                | pltInCnt | string | 팔레트입수 | "0" |
                | origin | string | 원산지 | "" |
                | distTermMgtYn | string | 유통기한관리여부 | "N" |
                | useTermDay | string | 사용기한 | "0" |
                | outCanDay | string | 출고가능일수 | "0" |
                | inCanDay | string | 입고가능일수 | "0" |
                | boxDiv | string | 출고박스 | "1" |
                | bufGodYn | string | 완충상품여부 | "Y" |
                | loadingDirection | string | 출고박스 상품 적재 기준 | "NONE" |
                | firstInDt | string | 최초입고일자 | null |
                | useYn | string | 사용여부 | "Y" |
                | feeYn | string | 요금적용여부 | "" |
                | saleUnitQty | string | 판매단위수량 | "1" |
                | cstOneDayDeliveryYn | string | 원데이배송여부 | null |
                | safetyStock | string | 안전재고수량 | "1" |
                """,
        security = {@SecurityRequirement(name = "bearer")},
        parameters = {
                @Parameter(
                        name = "categoryId",
                        in = ParameterIn.QUERY,
                        description = "카테고리 ID(없는 경우 전체 조회)",
                        schema = @Schema(type = "number")
                ),
                @Parameter(
                        name = "pricePolicyIds",
                        description = "가격 정책 ID 목록",
                        in = ParameterIn.QUERY,
                        schema = @Schema(type = "array")
                ),
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
                        description = "관리자 상품 목록 조회 성공",
                        content = @Content(
                                schema = @Schema(implementation = StringResponseSchema.class),
                                examples = @ExampleObject("""
                                        {
                                          "statusCode": 200,
                                          "code": "SUC01",
                                          "timestamp": "2026-02-03T12:12:30.013",
                                          "content": {
                                            "products": {
                                              "totalElements": 16,
                                              "hasNext": true,
                                              "nextCursor": 20,
                                              "items": [
                                                {
                                                  "id": 1,
                                                  "sellerId": 1,
                                                  "name": "스프링노트1",
                                                  "brandName": "노트왕",
                                                  "pricePolicy": {
                                                    "id": 28,
                                                    "price": 45000,
                                                    "discountPrice": 37000,
                                                    "discountRate": 3.2,
                                                    "accumulatedPoint": 1200
                                                  },
                                                  "sales": 0,
                                                  "productTags": [
                                                    {
                                                      "id": 25,
                                                      "productId": 1,
                                                      "name": "루테인",
                                                      "orderNum": null,
                                                      "status": "ACTIVE"
                                                    }
                                                  ],
                                                  "catalogImage": null,
                                                  "selectedOptions": null,
                                                  "stock": 99990,
                                                  "averageRating": 4.5,
                                                  "totalCount": 8,
                                                  "orderNum": 1,
                                                  "status": "ACTIVE",
                                                  "fasstoGoods": {
                                                    "godCd": "943881",
                                                    "godType": "1",
                                                    "godNm": "테스트 상품1",
                                                    "godTypeNm": "단품",
                                                    "invGodNmUseYn": "N",
                                                    "cstGodCd": "1",
                                                    "godOptCd1": "",
                                                    "godOptCd2": "",
                                                    "cstCd": "94388",
                                                    "cstNm": "마켓노트 주식회사 테스트",
                                                    "supCd": "",
                                                    "supNm": null,
                                                    "cateCd": "",
                                                    "cateNm": null,
                                                    "seasonCd": "0",
                                                    "genderCd": "A",
                                                    "godPr": "0",
                                                    "inPr": "0",
                                                    "salPr": "0",
                                                    "dealTemp": "03",
                                                    "pickFac": "",
                                                    "giftDiv": "01",
                                                    "giftDivNm": "본품",
                                                    "godWidth": "0.000",
                                                    "godLength": "0.000",
                                                    "godHeight": "0.000",
                                                    "makeYr": "",
                                                    "godBulk": "0",
                                                    "godWeight": "0",
                                                    "godSideSum": "0.000",
                                                    "godVolume": "",
                                                    "godBarcd": "",
                                                    "boxWidth": "0",
                                                    "boxLength": "0",
                                                    "boxHeight": "0",
                                                    "boxBulk": "0",
                                                    "boxWeight": "0",
                                                    "inBoxBarcd": null,
                                                    "inBoxLength": "0",
                                                    "inBoxHeight": "0",
                                                    "inBoxBulk": "0",
                                                    "inBoxWidth": "0",
                                                    "inBoxWeight": "0",
                                                    "inBoxSideSum": "0",
                                                    "boxInCnt": "0",
                                                    "inBoxInCnt": "0",
                                                    "pltInCnt": "0",
                                                    "origin": "",
                                                    "distTermMgtYn": "N",
                                                    "useTermDay": "0",
                                                    "outCanDay": "0",
                                                    "inCanDay": "0",
                                                    "boxDiv": "1",
                                                    "bufGodYn": "Y",
                                                    "loadingDirection": "NONE",
                                                    "firstInDt": null,
                                                    "useYn": "Y",
                                                    "feeYn": "",
                                                    "saleUnitQty": "1",
                                                    "cstOneDayDeliveryYn": null,
                                                    "safetyStock": "1"
                                                  }
                                                }
                                              ]
                                            }
                                          },
                                          "message": "관리자 상품 목록 조회 성공"
                                        }
                                        """)
                        )
                )
        })
public @interface GetAdminProductsApiDocs {
}
