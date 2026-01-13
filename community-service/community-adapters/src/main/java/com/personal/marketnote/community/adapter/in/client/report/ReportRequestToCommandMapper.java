package com.personal.marketnote.community.adapter.in.client.report;

import com.personal.marketnote.community.adapter.in.client.report.request.RegisterReportRequest;
import com.personal.marketnote.community.domain.report.ReportTargetType;
import com.personal.marketnote.community.port.in.command.report.ReportCommand;

public class ReportRequestToCommandMapper {
    public static ReportCommand mapToCommand(
            ReportTargetType targetType, Long targetId, Long userId, RegisterReportRequest request
    ) {
        return ReportCommand.builder()
                .targetType(targetType)
                .targetId(targetId)
                .reporterId(userId)
                .reason(request.getReason())
                .build();
    }
}
