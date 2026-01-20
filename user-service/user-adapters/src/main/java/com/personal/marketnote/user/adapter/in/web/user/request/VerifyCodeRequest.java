package com.personal.marketnote.user.adapter.in.web.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.personal.marketnote.common.domain.exception.ExceptionMessage.INVALID_EMAIL_EXCEPTION_MESSAGE;
import static com.personal.marketnote.common.utility.RegularExpressionConstant.EMAIL_PATTERN;

@Getter
public class VerifyCodeRequest {
    @Schema(
            name = "email",
            description = "이메일 주소",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "이메일 주소는 필수값입니다.")
    @Pattern(regexp = EMAIL_PATTERN, message = INVALID_EMAIL_EXCEPTION_MESSAGE)
    private String email;

    @Schema(
            name = "verificationCode",
            description = "인증 코드",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "인증 코드는 필수값입니다.")
    private String verificationCode;
}
