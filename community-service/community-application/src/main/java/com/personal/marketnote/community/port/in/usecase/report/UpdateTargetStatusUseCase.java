package com.personal.marketnote.community.port.in.usecase.report;

import com.personal.marketnote.community.port.in.command.report.UpdateTargetStatusCommand;

public interface UpdateTargetStatusUseCase {
    /**
     * @param command 대상 노출/숨기기 커맨드
     * @Date 2026-01-15
     * @Author 성효빈
     * @Description 대상을 노출/숨기기합니다.
     */
    void updateTargetStatus(UpdateTargetStatusCommand command);
}
