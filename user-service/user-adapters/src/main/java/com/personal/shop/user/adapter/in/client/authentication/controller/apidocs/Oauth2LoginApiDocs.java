package com.personal.shop.user.adapter.in.client.authentication.controller.apidocs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation(
        summary = "OAuth 2.0 콜백 URI",
        description = """
                작성일자: 2025-10-22
                
                작성자: 성효빈
                
                ## Description
                
                - 권한 서버에서 리다이렉트되는 콜백 엔드포인트입니다.
                
                - query로 전달된 code(필수), state(선택)를 사용해 소셜 토큰 교환 → 내부 JWT 발급 후,
                  308 리다이렉트로 프론트 `/redirection`으로 이동합니다.
                
                - refresh_token은 HttpOnly 쿠키로 세팅됩니다.
                
                - access-token은 Location 쿼리 파라미터로 전달됩니다.
                
                ---
                
                ## Request
                
                | **키** | **타입** | **설명** | **필수 여부** | **예시** |
                | --- | --- | --- | --- | --- |
                | authVendor(path) | string | OAuth 2.0 공급자 | Y | "google", "kakao" |
                | code(query) | string | 인가 코드 | Y | "4/0Abc..." |
                | state(query) | string | 리다이렉션 대상(클라이언트 오리진) | N | "http://localhost:3000" |
                
                ---
                
                ## Response
                
                - HTTP 308 Permanent Redirect
                - Body는 없고, 아래 Header가 포함됩니다.
                
                ### Response Headers
                
                | **헤더** | **설명** | **예시** |
                | --- | --- | --- |
                | Set-Cookie | refresh_token(HttpOnly, Secure, SameSite=None) | "refresh_token=...; Path=/; Domain=localhost; Max-Age=2592000000; Secure; HttpOnly; SameSite=None" |
                | Location | 프론트 리다이렉션 URL | "http://localhost:3000/redirection?type=oauth2&is-success=true&access-token=...&is-guest=true&auth-vendor=google&userJpaEntity-name=홍길동" |
                
                ---
                
                실패 시에도 308으로 프론트 `/redirection?type=oauth2&is-success=false` 로 이동합니다.
                """,
        parameters = {
                @Parameter(
                        name = "authVendor",
                        in = ParameterIn.PATH,
                        required = true,
                        description = "OAuth 2.0 공급자",
                        schema = @Schema(type = "string", example = "google")
                ),
                @Parameter(
                        name = "code",
                        in = ParameterIn.QUERY,
                        required = true,
                        description = "인가 코드",
                        schema = @Schema(type = "string", example = "4/0Abc...")
                ),
                @Parameter(
                        name = "state",
                        in = ParameterIn.QUERY,
                        required = false,
                        description = "리다이렉션 대상(클라이언트 오리진)",
                        schema = @Schema(type = "string", example = "http://localhost:3000")
                )
        },
        responses = {
                @ApiResponse(
                        responseCode = "308",
                        description = "소셜 로그인 성공 - 프론트로 리다이렉션",
                        headers = {
                                @Header(name = HttpHeaders.SET_COOKIE, description = "refresh_token; HttpOnly; Secure; SameSite=None"),
                                @Header(name = HttpHeaders.LOCATION, description = "리다이렉션 대상 URL (/redirection?type=oauth2&is-success=true&...)")
                        },
                        content = @Content(
                                examples = @ExampleObject(
                                        name = "redirect",
                                        value = "Location: http://localhost:3000/redirection?type=oauth2&is-success=true&access-token=eyJ...&is-guest=true&auth-vendor=google&userJpaEntity-name=홍길동\nSet-Cookie: refresh_token=eyJ...; Path=/; Domain=localhost; Max-Age=2592000000; Secure; HttpOnly; SameSite=None"
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "308",
                        description = "소셜 로그인 실패 - 프론트로 리다이렉션(is-success=false)",
                        headers = {
                                @Header(name = HttpHeaders.LOCATION, description = "리다이렉션 대상 URL (/redirection?type=oauth2&is-success=false)")
                        }
                )
        }
)

public @interface Oauth2LoginApiDocs {
}
