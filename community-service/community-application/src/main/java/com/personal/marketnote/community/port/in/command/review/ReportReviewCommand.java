package com.personal.marketnote.community.port.in.command.review;

public record ReportReviewCommand(
        Long id,
        Long reporterId,
        String reason
) {
    public static ReportReviewCommand of(
            Long id,
            Long reporterId,
            String reason
    ) {
        return new ReportReviewCommand(id, reporterId, reason);
    }
}
