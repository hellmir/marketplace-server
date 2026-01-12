package com.personal.marketnote.community.adapter.in.client.review.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReportReviewRequest {
    @Schema(
            name = "reason",
            description = "리뷰 신고 사유",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "리뷰 신고 사유는 필수값입니다.")
    @Size(min = 1, max = 2047, message = "리뷰 신고 사유는 1자 이상 2047자 이하로 입력해야 합니다.")
    private String reason;
}
