package com.personal.marketnote.user.adapter.in.client.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.personal.marketnote.common.domain.exception.ExceptionMessage.INVALID_EMAIL_EXCEPTION_MESSAGE;
import static com.personal.marketnote.common.utility.RegularExpressionConstant.EMAIL_PATTERN;

@Getter
public class SignInRequest {
    @Schema(
            name = "email",
            description = "이메일 주소",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    @Pattern(regexp = EMAIL_PATTERN, message = INVALID_EMAIL_EXCEPTION_MESSAGE)
    private String email;

    @Schema(
            name = "password",
            description = "비밀번호",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String password;
}
