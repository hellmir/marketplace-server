package com.personal.marketnote.common.adapter.in.api.format;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * <p>공통 응답 형식.
 * <p>예)</p>
 * <code>
 * {
 * "statusCode": 200,
 * "timestamp": "2025-12-26H17:06:00.222",
 * "content": {
 * "id": 200,
 * "name": "홍길동"
 * },
 * "message": "Hello, World!"
 * }
 * </code>
 *
 * @param <T> 응답 처리 결과의 타입.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class BaseResponse<T> {
    /**
     * 응답의 상태 코드.
     */
    private int statusCode;

    /**
     * 응답 코드.
     */
    private String code;

    /**
     * 응답이 전송되는 시간을 나타내는 타임스탬프.
     */
    private LocalDateTime timestamp;

    /**
     * 응답 처리 결과, 실제 데이터를 나타내는 필드.
     */
    private T content;

    /**
     * 클라이언트로 전달되는 메시지.
     */
    private String message;

    public static <T> BaseResponse<T> of(T content, HttpStatus status, String code, String message) {
        return of(content, status.value(), code, message);
    }

    public static <T> BaseResponse<T> of(T content, int statusCode, String code, String message) {
        return new BaseResponse<>(statusCode, code, LocalDateTime.now(), content, message);
    }

    public static <T> BaseResponse<T> of(T content, int statusCode, String code) {
        return of(content, statusCode, code, null);
    }

    public static <T> BaseResponse<T> of(T content, HttpStatus status, String code) {
        return of(content, status, code, null);
    }

    public static <T> BaseResponse<T> of(int statusCode, String code) {
        return of(null, statusCode, code);
    }

    public static <T> BaseResponse<T> of(HttpStatus status, String code) {
        return of(null, status, code);
    }

    public static <T> BaseResponse<T> of(int statusCode, String code, String message) {
        return of(null, statusCode, code, message);
    }

    public static <T> BaseResponse<T> of(HttpStatus status, String code, String message) {
        return of(null, status, code, message);
    }
}
