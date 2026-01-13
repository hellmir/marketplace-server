package com.personal.marketnote.community.port.out.report;

import com.personal.marketnote.community.domain.report.Report;

public interface SaveReportPort {
    void save(Report report);
}
