package com.anhtuan.bookapp.service.implement;

import com.anhtuan.bookapp.domain.Report;
import com.anhtuan.bookapp.repository.base.ReportRepository;
import com.anhtuan.bookapp.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    @Override
    public void insertReport(Report report) {
        reportRepository.insert(report);
    }

    @Override
    public List<Report> findReportsByStatus(Integer status) {
        return reportRepository.findReportsByStatus(status);
    }

    @Override
    public void updateStatusById(String id, int status) {
        reportRepository.updateStatusById(id, status);
    }
}
