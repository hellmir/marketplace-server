package com.personal.marketnote.user.adapter.in.client.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LogoutRequest {
    @Schema(
            name = "accessToken",
            description = "액세스 토큰",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String accessToken;

    @Schema(
            name = "refreshToken",
            description = "리프레시 토큰",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private String refreshToken;
}
