package com.personal.marketnote.community.service.review;

import com.personal.marketnote.common.application.UseCase;
import com.personal.marketnote.community.domain.review.ReviewReport;
import com.personal.marketnote.community.port.in.command.review.ReportReviewCommand;
import com.personal.marketnote.community.port.in.usecase.review.GetReviewUseCase;
import com.personal.marketnote.community.port.in.usecase.review.ReportReviewUseCase;
import com.personal.marketnote.community.port.out.review.SaveReviewReportPort;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@UseCase
@RequiredArgsConstructor
@Transactional(isolation = READ_COMMITTED)
public class ReportReviewService implements ReportReviewUseCase {
    private final GetReviewUseCase getReviewUseCase;
    private final SaveReviewReportPort saveReviewReportPort;

    @Override
    public void reportReview(ReportReviewCommand command) {
        Long id = command.id();
        Long reporterId = command.reporterId();
        getReviewUseCase.validateDuplicateReport(id, reporterId);

        saveReviewReportPort.save(
                ReviewReport.of(id, reporterId, command.reason())
        );
    }
}
