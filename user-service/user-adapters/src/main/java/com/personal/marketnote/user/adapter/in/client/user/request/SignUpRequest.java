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
    @Pattern(regexp = "^[가-힣]{2,20}$", message = "닉네임은 2글자 이상, 20글자 이하여야 합니다.")
    private String nickname;

    @Schema(
            name = "fullName",
            description = "성명",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "\"fullName\" should not be empty. (+ not null)")
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "성명은 2글자 이상, 10글자 이하여야 합니다.")
    private String fullName;

    @Schema(
            name = "phoneNumber",
            description = "전화번호",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotEmpty(message = "\"phoneNumber\" should not be empty. (+ not null)")
    @Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;
}
