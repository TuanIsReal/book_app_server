package com.anhtuan.bookapp.service.base;

import com.anhtuan.bookapp.domain.Report;

import java.util.List;

public interface ReportService {
    void insertReport(Report report);
    List<Report> findReportsByStatus(Integer status);
    void updateStatusById(String id, int status);
}
