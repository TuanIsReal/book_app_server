package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.common.ResponseCode;
import com.anhtuan.bookapp.config.Constant;
import com.anhtuan.bookapp.domain.CustomUserDetails;
import com.anhtuan.bookapp.domain.Report;
import com.anhtuan.bookapp.request.AddReportRequest;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.BookService;
import com.anhtuan.bookapp.service.base.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("report")
@AllArgsConstructor
public class ReportController {
    private final ReportService reportService;
    private final BookService bookService;

    @PostMapping("addReport")
    public ResponseEntity<Response> addReport(Authentication authentication,
                          @RequestBody AddReportRequest request){
        Response response = new Response();
        if (authentication == null) {
            response.setCode(ResponseCode.USER_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId();

        if (bookService.findBookById(request.getBookId()) == null){
            response.setCode(ResponseCode.BOOK_NOT_EXISTS);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        Report report = new Report(request);
        report.setUserId(userId);
        report.setStatus(Constant.REPORT_STATUS.NOT_CHECK);
        report.setReportTime(System.currentTimeMillis());
        reportService.insertReport(report);

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("getReports")
    @Secured("ADMIN")
    public ResponseEntity<Response> getReports(@RequestParam Integer status){
        Response response = new Response();

        if (status != Constant.REPORT_STATUS.NOT_CHECK && status != Constant.REPORT_STATUS.CHECKED){
            response.setCode(ResponseCode.WRONG_DATA_FORMAT);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        List<Report> reportList = reportService.findReportsByStatus(status);

        response.setCode(ResponseCode.SUCCESS);
        response.setData(reportList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("updateReport")
    @Secured("ADMIN")
    public ResponseEntity<Response> updateReport(@RequestParam String id,
                                                  @RequestParam Integer status){
        Response response = new Response();

        if (status != Constant.REPORT_STATUS.NOT_CHECK && status != Constant.REPORT_STATUS.CHECKED){
            response.setCode(ResponseCode.WRONG_DATA_FORMAT);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        reportService.updateStatusById(id, status);

        response.setCode(ResponseCode.SUCCESS);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
