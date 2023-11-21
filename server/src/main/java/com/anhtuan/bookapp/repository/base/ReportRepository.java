package com.anhtuan.bookapp.repository.base;

import com.anhtuan.bookapp.domain.Report;
import com.anhtuan.bookapp.repository.customize.ReportCustomizeRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Report, String>, ReportCustomizeRepository {
    List<Report> findReportsByStatus(Integer status);
}
