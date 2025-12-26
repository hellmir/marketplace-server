package com.personal.shop.user.adapter.in.client.user.request;

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
    @Pattern(regexp = "^[가-힣]{2,10}$", message = "닉네임은 2글자 이상, 10글자 이하여야 합니다.")
    private String nickname;
}
