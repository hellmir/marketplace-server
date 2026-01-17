package com.personal.marketnote.reward.adapter.in.offerwall.apidocs;

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
        summary = "애드팝콘 리워드 콜백",
        description = """
                작성일자: 2026-01-17
                
                작성자: 성효빈
                
                ---
                
                ## Description
                
                - 애드팝콘 리워드 적립 콜백을 처리합니다.
                
                - 서명 검증에 실패하거나 중복 지급인 경우 오류 코드를 반환합니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | reward_key | string | 리워드 키 | Y | 20241211-abc |
                | usn | string | 사용자 식별자 | Y | U1000 |
                | campaign_key | string | 캠페인 키 | Y | C123 |
                | user_device_type | string | 사용자 디바이스 타입 | Y | ANDROID |
                | campaign_type | number | 캠페인 타입 | N | 1 |
                | campaign_name | string | 캠페인 이름 | N | "앱 설치" |
                | quantity | number | 지급 수량 | Y | 100 |
                | signed_value | string | 서명 값 | Y | "abc123" |
                | app_key | number | 앱 키 | N | 10 |
                | app_name | string | 앱 이름 | N | "my-app" |
                | adid | string | 구글 광고 ID | N | "adid-1" |
                | idfa | string | IDFA | N | "idfa-1" |
                | time_stamp | string | 캠페인 완료 일시 | N | "2026-01-17T12:00:00" |
                
                ---
                
                ## Response
                
                성공/실패 시 각 코드와 메시지를 포함한 JSON 문자열 반환
                """,
        parameters = {
                @Parameter(name = "reward_key", in = ParameterIn.QUERY, required = true, description = "리워드 키", schema = @Schema(type = "string", example = "20241211-abc")),
                @Parameter(name = "usn", in = ParameterIn.QUERY, required = true, description = "사용자 식별자", schema = @Schema(type = "string", example = "U1000")),
                @Parameter(name = "campaign_key", in = ParameterIn.QUERY, required = true, description = "캠페인 키", schema = @Schema(type = "string", example = "C123")),
                @Parameter(name = "user_device_type", in = ParameterIn.QUERY, required = true, description = "사용자 디바이스 타입", schema = @Schema(type = "string", example = "ANDROID")),
                @Parameter(name = "campaign_type", in = ParameterIn.QUERY, description = "캠페인 타입", schema = @Schema(type = "number", example = "1")),
                @Parameter(name = "campaign_name", in = ParameterIn.QUERY, description = "캠페인 이름", schema = @Schema(type = "string", example = "앱 설치")),
                @Parameter(name = "quantity", in = ParameterIn.QUERY, required = true, description = "지급 수량", schema = @Schema(type = "number", example = "100")),
                @Parameter(name = "signed_value", in = ParameterIn.QUERY, required = true, description = "서명 값", schema = @Schema(type = "string", example = "abc123")),
                @Parameter(name = "app_key", in = ParameterIn.QUERY, description = "앱 키", schema = @Schema(type = "number", example = "10")),
                @Parameter(name = "app_name", in = ParameterIn.QUERY, description = "앱 이름", schema = @Schema(type = "string", example = "my-app")),
                @Parameter(name = "adid", in = ParameterIn.QUERY, description = "구글 광고 ID", schema = @Schema(type = "string", example = "adid-1")),
                @Parameter(name = "idfa", in = ParameterIn.QUERY, description = "IDFA", schema = @Schema(type = "string", example = "idfa-1")),
                @Parameter(name = "time_stamp", in = ParameterIn.QUERY, description = "캠페인 완료 일시", schema = @Schema(type = "string", example = "2026-01-17T12:00:00"))
        },
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "성공 또는 실패(결과 코드는 본문 참고)",
                        content = @Content(
                                mediaType = "application/json",
                                examples = {
                                        @ExampleObject(
                                                name = "성공",
                                                value = """
                                                        {
                                                          "Result": true,
                                                          "ResultCode": 1,
                                                          "ResultMsg": "success"
                                                        }
                                                        """
                                        ),
                                        @ExampleObject(
                                                name = "서명 검증 실패",
                                                value = """
                                                        {
                                                          "Result": false,
                                                          "ResultCode": 3101,
                                                          "ResultMsg": "signature verification failed"
                                                        }
                                                        """
                                        ),
                                        @ExampleObject(
                                                name = "회원 정보 존재하지 않음",
                                                value = """
                                                        {
                                                          "Result": false,
                                                          "ResultCode": 3200,
                                                          "ResultMsg": "invalid user"
                                                        }
                                                        """
                                        ),
                                        @ExampleObject(
                                                name = "중복 지급",
                                                value = """
                                                        {
                                                          "Result": false,
                                                          "ResultCode": 3100,
                                                          "ResultMsg": "duplicate transaction"
                                                        }
                                                        """
                                        ),
                                        @ExampleObject(
                                                name = "그 외 오류",
                                                value = """
                                                        {
                                                          "Result": false,
                                                          "ResultCode": 4000,
                                                          "ResultMsg": "custom error message"
                                                        }
                                                        """
                                        )
                                }
                        )
                )
        }
)
public @interface AdpopcornCallbackApiDocs {
}
