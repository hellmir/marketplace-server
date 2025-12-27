package com.personal.marketnote.user.adapter.in.client.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpRequest {
    @Schema(
            name = "nickname",
            description = "닉네임",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "\"nickname\" should not be empty. (+ not null)")
    @Pattern(regexp = "^[가-힣]{1,6}$", message = "닉네임은 한글만 가능하며, 6글자 이하여야 합니다.")
    private String nickname;

    @Schema(
            name = "email",
            description = "이메일 주소"
    )
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 주소 형식이 올바르지 않습니다.")
    private String email;

    @Schema(
            name = "password",
            description = "비밀번호"
    )
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 8자 이상, 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @Schema(
            name = "fullName",
            description = "성명"
    )
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "성명은 2글자 이상, 10글자 이하여야 합니다.")
    private String fullName;

    @Schema(
            name = "phoneNumber",
            description = "전화번호"
    )
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;
}
