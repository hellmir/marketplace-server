package com.personal.marketnote.community.port.in.usecase.report;

import com.personal.marketnote.community.port.in.command.report.ReportCommand;

public interface RegisterReportUseCase {
    /**
     * @param command 신고 커맨드
     * @Date 2026-01-12
     * @Author 성효빈
     * @Description 리뷰 또는 게시글을 신고합니다.
     */
    void report(ReportCommand command);
}
