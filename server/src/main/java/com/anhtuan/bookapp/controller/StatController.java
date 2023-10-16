package com.anhtuan.bookapp.controller;

import com.anhtuan.bookapp.config.Constant.*;
import com.anhtuan.bookapp.domain.Payment;
import com.anhtuan.bookapp.domain.TransactionHistory;
import com.anhtuan.bookapp.response.IncomeAdminResponse;
import com.anhtuan.bookapp.response.IncomeMemberResponse;
import com.anhtuan.bookapp.response.Response;
import com.anhtuan.bookapp.service.base.PaymentService;
import com.anhtuan.bookapp.service.base.TransactionHistoryService;
import com.anhtuan.bookapp.service.base.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("stat")
@AllArgsConstructor
public class StatController {
    private PaymentService paymentService;
    private TransactionHistoryService transactionHistoryService;

    @PostMapping("incomeMember")
    public ResponseEntity<Response> incomeMember(@RequestParam String userId,
                                                 @RequestParam String startDate,
                                                 @RequestParam String endDate){
        Response response = new Response();
        IncomeMemberResponse incomeMemberResponse = new IncomeMemberResponse();
        long start, end;
        int revenue = 0, spend = 0;
        List<TransactionHistory> transactionHistoryList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date startD = format.parse(startDate);
            Date endD = format.parse(endDate);
            start = startD.getTime();
            end = endD.getTime();
            transactionHistoryList = transactionHistoryService.findIncomeMember(userId, start, end);
            incomeMemberResponse.setTransactionHistoryList(transactionHistoryList);

            for (TransactionHistory transactionHistory: transactionHistoryList){
                if (transactionHistory.getTransactionType() == TRANSACTION_TYPE.SELL_BOOK){
                    revenue += transactionHistory.getPoint();
                }
                if (transactionHistory.getTransactionType() == TRANSACTION_TYPE.BUY_BOOK){
                    spend += transactionHistory.getPoint();
                }
            }
            incomeMemberResponse.setRevenue(revenue);
            incomeMemberResponse.setSpend(spend * -1);
            incomeMemberResponse.setChange(revenue + spend);
        } catch (ParseException e) {
            response.setCode(99);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(incomeMemberResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("incomeAdmin")
    public ResponseEntity<Response> incomeAdmin(@RequestParam String startDate,
                                                 @RequestParam String endDate){
        Response response = new Response();
        long start, end;
        int totalPoint = 0;
        long totalMoney = 0;
        int revenue = 0, spend = 0;
        IncomeAdminResponse incomeAdminResponse  = new IncomeAdminResponse();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date startD = format.parse(startDate);
            Date endD = format.parse(endDate);
            start = startD.getTime();
            end = endD.getTime();

            List<Payment> paymentList = paymentService.getIncomeAdmin(start, end);
            for (Payment payment:paymentList){
                totalPoint += payment.getPoint();
                totalMoney += payment.getMoney();
            }

            List<TransactionHistory> transactionHistoryList = transactionHistoryService.findIncomeAdmin(start, end);
            for (TransactionHistory transactionHistory: transactionHistoryList){
                if (transactionHistory.getTransactionType() == TRANSACTION_TYPE.SELL_BOOK){
                    revenue += transactionHistory.getPoint();
                }
                if (transactionHistory.getTransactionType() == TRANSACTION_TYPE.BUY_BOOK){
                    spend += transactionHistory.getPoint();
                }
            }

            incomeAdminResponse.setTotalPoint(totalPoint);
            incomeAdminResponse.setTotalMoney(totalMoney);
            incomeAdminResponse.setTransactionPoint(-1 * (revenue + spend));
            incomeAdminResponse.setPaymentList(paymentList);

        } catch (ParseException e) {
            response.setCode(99);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setCode(100);
        response.setData(incomeAdminResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
